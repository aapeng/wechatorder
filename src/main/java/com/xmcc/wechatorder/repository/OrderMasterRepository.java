package com.xmcc.wechatorder.repository;

import com.xmcc.wechatorder.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
}