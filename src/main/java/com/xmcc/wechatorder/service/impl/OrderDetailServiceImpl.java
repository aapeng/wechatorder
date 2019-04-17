package com.xmcc.wechatorder.service.impl;

import com.xmcc.wechatorder.dao.BatchDao;
import com.xmcc.wechatorder.dao.impl.BatchDaoImpl;
import com.xmcc.wechatorder.entity.OrderDetail;
import com.xmcc.wechatorder.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailServiceImpl  implements OrderDetailService {

    @Autowired
    private BatchDao<OrderDetail> batchDao;

    @Override
    @Transactional
    public void batchInsert(List<OrderDetail> orderDetailList) {
        batchDao.batchInsert(orderDetailList);
    }
}
