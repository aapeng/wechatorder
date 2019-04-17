package com.xmcc.wechatorder.service.impl;

import com.xmcc.wechatorder.common.ProductEnums;
import com.xmcc.wechatorder.common.ResultEnums;
import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.dto.ProductCategoryDto;
import com.xmcc.wechatorder.dto.ProductInfoDto;
import com.xmcc.wechatorder.entity.ProductInfo;
import com.xmcc.wechatorder.repository.ProductInfoRepository;
import com.xmcc.wechatorder.service.ProductCategoryService;
import com.xmcc.wechatorder.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    public ResultResponse queryList() {
        ResultResponse<List<ProductCategoryDto>> resultResponse = productCategoryService.findAll();
        List<ProductCategoryDto> productCategoryDtoList = resultResponse.getData();
        if (productCategoryDtoList == null || productCategoryDtoList.size() == 0 ) {
            return resultResponse;
        }
        List<Integer> categoryTypeList = productCategoryDtoList.stream()
                .map(productCategoryDto -> productCategoryDto.getCategoryType())
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatusAndCategoryTypeIn(ProductEnums.PRODUCT_UP.getCode(), categoryTypeList);
        List<ProductCategoryDto> finalList = productCategoryDtoList.parallelStream()
                .map(productCategoryDto -> {
                    productCategoryDto.setProductInfoDtoList(productInfoList.stream()
                    .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList()));
            return productCategoryDto;
        }).collect(Collectors.toList());
        return ResultResponse.success(finalList);
    }

    @Override
    public ResultResponse<ProductInfo> queryById(String productId) {
        if (productId == null && productId.equals("")){//判断参数是否空
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg());
        }
        Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(productId);
        if (!productInfoOptional.isPresent()){//判断产品是否存在
            return ResultResponse.fail(ProductEnums.NOT_EXITS.getMsg());
        }
        ProductInfo productInfo = productInfoOptional.get();
        if (productInfo.getProductStatus() == ProductEnums.PRODUCT_DOWN.getCode()){//判断产品是否下架
            return ResultResponse.fail(ProductEnums.PRODUCT_DOWN.getMsg());
        }
        return ResultResponse.success(productInfo);
    }

    @Override
    @Transactional
    public void update(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }
}
