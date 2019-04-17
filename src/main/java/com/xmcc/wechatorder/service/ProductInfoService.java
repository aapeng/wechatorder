package com.xmcc.wechatorder.service;

import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.entity.ProductInfo;

public interface ProductInfoService {

    ResultResponse queryList();

    ResultResponse<ProductInfo> queryById(String productId);

    void update(ProductInfo productInfo);

}
