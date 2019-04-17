package com.xmcc.wechatorder.service;

import com.xmcc.wechatorder.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    void batchInsert(List<OrderDetail> orderDetailList);
}
