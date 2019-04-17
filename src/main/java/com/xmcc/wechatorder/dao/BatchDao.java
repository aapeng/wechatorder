package com.xmcc.wechatorder.dao;

import java.util.List;

/**
 * 批量操作
 * @param <T>
 */
public interface BatchDao<T> {

    void batchInsert(List<T> list);
}
