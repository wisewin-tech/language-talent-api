package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.KeyValuesBO;

import java.util.List;

public interface KeyValDAO {
    /**
     *通过配置表key查询对应的value
     * @return
     */
    String selectKey(String key);

    /**
     * 查询所有key valye
     */
    List<KeyValuesBO> selectKeys();


}
