package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.DictionariesBO;

import java.util.List;

/**
 *  字典表
 */
public interface DictionariesDAO {

    /**
     * 根据outer连接字典类型表来查找数据
     *     Integer id; //字典id
     *     String key; //类型名字
      *    String value; //类型
     *    String outer;连接字典类型表valuename
     */
    List<DictionariesBO> queryDictionaries(DictionariesBO dictionariesBO);

}
