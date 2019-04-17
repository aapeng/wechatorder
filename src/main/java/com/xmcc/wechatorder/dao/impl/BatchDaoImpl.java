package com.xmcc.wechatorder.dao.impl;

import com.xmcc.wechatorder.dao.BatchDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BatchDaoImpl<T> implements BatchDao<T> {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public void batchInsert(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if (i % 100 == 0 || i == list.size() - 1) {//每100条执行一次写入数据库的操作
                em.flush();
                em.clear();
            }
        }
    }
}
