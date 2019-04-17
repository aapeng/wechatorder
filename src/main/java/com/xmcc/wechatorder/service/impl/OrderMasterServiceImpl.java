package com.xmcc.wechatorder.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmcc.wechatorder.common.*;
import com.xmcc.wechatorder.dto.OrderDetailDto;
import com.xmcc.wechatorder.dto.OrderMasterDto;
import com.xmcc.wechatorder.entity.OrderDetail;
import com.xmcc.wechatorder.entity.OrderMaster;
import com.xmcc.wechatorder.entity.ProductInfo;
import com.xmcc.wechatorder.exception.CustomException;
import com.xmcc.wechatorder.repository.OrderMasterRepository;
import com.xmcc.wechatorder.service.OrderDetailService;
import com.xmcc.wechatorder.service.OrderMasterService;
import com.xmcc.wechatorder.service.ProductInfoService;
import com.xmcc.wechatorder.util.BigDecimalUtil;
import com.xmcc.wechatorder.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public ResultResponse create(OrderMasterDto orderMasterDto) {
        List<OrderDetailDto> items = orderMasterDto.getItems();//在controller进行参数校验，此处取出订单项即可
        List<OrderDetail> orderDetailList = Lists.newArrayList();//创建订单detail 集合 将符合的放入其中 待会批量插入
        BigDecimal totalPrice = new BigDecimal("0");//创建订单总金额为0  涉及到钱的都用 高精度计算
        for (OrderDetailDto orderDetailDto:items) {
            ResultResponse<ProductInfo> productInfoResultResponse = productInfoService.queryById(orderDetailDto.getProductId());
            if (productInfoResultResponse.getCode() == ResultEnums.FAIL.getCode()) {//判断产品是否获取
                // 生成订单失败，因为涉及到事务，需要抛出异常，事务机制遇到异常才会回滚
                throw new CustomException(productInfoResultResponse.getMsg());
            }
            ProductInfo productInfo = productInfoResultResponse.getData();
            if (productInfo.getProductStock() < orderDetailDto.getProductQuantity()){//判断库存
                throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //构建订单项，放入集合中：将前台传入的订单项DTO与数据库查询到的商品数据组装成OrderDetail
            OrderDetail orderDetail = OrderDetail.builder().detailId(IdUtil.createIdbyUUID()).productId(productInfo.getProductId())
                    .productIcon(productInfo.getProductIcon()).productName(productInfo.getProductName())
                    .productPrice(productInfo.getProductPrice()).productQuantity(orderDetailDto.getProductQuantity()).build();
            orderDetailList.add(orderDetail);
            //减少产品的库存
            productInfo.setProductStock(productInfo.getProductStock() - orderDetailDto.getProductQuantity());
            productInfoService.update(productInfo);
            //计算订单总金额
            totalPrice = BigDecimalUtil.add(totalPrice,BigDecimalUtil.multi(productInfo.getProductPrice(),orderDetailDto.getProductQuantity()));
        }
        //创建订单id
        String orderId = IdUtil.createIdbyUUID();
        //将订单id设置到订单项中
        List<OrderDetail> orderDetailList2 = orderDetailList.stream()
                .map(orderDetail -> {
                    orderDetail.setOrderId(orderId);
                    return orderDetail;
                })
                .collect(Collectors.toList());
        //将订单项批量添加到数据库
        orderDetailService.batchInsert(orderDetailList2);
        //构建订单
        OrderMaster orderMaster = OrderMaster.builder().buyerAddress(orderMasterDto.getAddress()).
                buyerName(orderMasterDto.getName()).buyerOpenid(orderMasterDto.getOpenid())
                .buyerPhone(orderMasterDto.getPhone()).orderAmount(totalPrice).orderId(orderId)
                .orderStatus(OrderEnums.NEW.getCode()).payStatus(PayEnums.WAIT.getCode()).build();
        orderMasterRepository.save(orderMaster);

        HashMap<String,String> map = Maps.newHashMap();
        map.put("orderId",orderId);
        return ResultResponse.success(map);
    }
}
