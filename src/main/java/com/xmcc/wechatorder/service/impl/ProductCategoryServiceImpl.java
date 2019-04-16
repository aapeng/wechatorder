package com.xmcc.wechatorder.service.impl;

import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.dto.ProductCategoryDto;
import com.xmcc.wechatorder.entity.ProductCategory;
import com.xmcc.wechatorder.repository.ProductCategoryRepository;
import com.xmcc.wechatorder.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ResultResponse<List<ProductCategoryDto>> findAll() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        List<ProductCategoryDto> productCategoryDtoList = productCategoryList.stream()
                .map(productCategory -> ProductCategoryDto.build(productCategory))
                .collect(Collectors.toList());
        return ResultResponse.success(productCategoryDtoList);
    }
}
