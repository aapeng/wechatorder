package com.xmcc.wechatorder.repository;

import com.xmcc.wechatorder.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    @Query(value = "select om from OrderMaster as om where buyerOpenid = ?1 ")
    Page<OrderMaster> findByopenid(String openId, Pageable pageable);
}