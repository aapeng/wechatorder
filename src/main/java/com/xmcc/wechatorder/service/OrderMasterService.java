package com.xmcc.wechatorder.service;

import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.dto.OrderMasterDto;

public interface OrderMasterService {

    ResultResponse create(OrderMasterDto orderMasterDto);//创建订单

    ResultResponse list(String openid,Integer page,Integer size);//订单列表，分页

    ResultResponse detail(String openid,String orderId);//订单详情

    ResultResponse cancel(String openid,String orderId);//取消订单
}
