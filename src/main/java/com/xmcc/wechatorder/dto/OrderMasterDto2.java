package com.xmcc.wechatorder.dto;

import com.xmcc.wechatorder.entity.OrderDetail;
import com.xmcc.wechatorder.entity.OrderMaster;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class OrderMasterDto2 extends OrderMaster {

    private  List<OrderDetail> orderDetailList;

    public static OrderMasterDto2 build(OrderMaster orderMaster){
        OrderMasterDto2 orderMasterDto2 = new OrderMasterDto2();
        BeanUtils.copyProperties(orderMaster, orderMasterDto2);
        return orderMasterDto2;
    }
}
