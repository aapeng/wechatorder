package com.xmcc.wechatorder.service;

import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.dto.OrderMasterDto;

public interface OrderMasterService {

    ResultResponse create(OrderMasterDto orderMasterDto);
}
