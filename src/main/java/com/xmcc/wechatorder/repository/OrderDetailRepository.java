package com.xmcc.wechatorder.repository;

import com.xmcc.wechatorder.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
}
