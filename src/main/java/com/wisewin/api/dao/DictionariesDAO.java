package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.DictionariesBO;

import java.util.List;

/**
 *  字典表
 */
public interface DictionariesDAO {

    /**
     * 查询字典value
     */
    List<DictionariesBO> queryDictionaries(String name);

}
