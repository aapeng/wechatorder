package com.xmcc.wechatorder.service.impl;

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

import java.util.List;
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
        List<Integer> categoryTypeList = productCategoryDtoList.stream().map(productCategoryDto -> productCategoryDto.getCategoryType()).collect(Collectors.toList());
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), categoryTypeList);
        List<ProductCategoryDto> finalList = productCategoryDtoList.parallelStream().map(productCategoryDto -> {
            productCategoryDto.setProductInfoDtoList(productInfoList.stream()
                    .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList()));
            return productCategoryDto;
        }).collect(Collectors.toList());
        return ResultResponse.success(finalList);
    }
}
