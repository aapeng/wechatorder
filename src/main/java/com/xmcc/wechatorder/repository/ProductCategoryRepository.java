package com.xmcc.wechatorder.repository;

import com.xmcc.wechatorder.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
}
