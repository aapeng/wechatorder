package com.xmcc.wechatorder.service;

import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {

    ResultResponse<List<ProductCategoryDto>> findAll();
}
