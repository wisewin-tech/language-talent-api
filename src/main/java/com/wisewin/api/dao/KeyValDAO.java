package com.wisewin.api.dao;


public interface KeyValDAO {
    /**
     *通过配置表key查询对应的value
     * @return
     */
    String selectKey(String key);


}
