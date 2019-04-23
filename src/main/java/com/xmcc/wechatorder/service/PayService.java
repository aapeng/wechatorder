package com.xmcc.wechatorder.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.xmcc.wechatorder.entity.OrderMaster;

public interface PayService {

    OrderMaster findById(String orderId);

    PayResponse create(OrderMaster orderMaster);

    void notify(String notifyData);

    RefundResponse refund(OrderMaster orderMaster);
}
