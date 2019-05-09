package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.SpecialClassBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专题分类
 * */
public interface SpecialClassDAO {

    /**
     * 三条专题分类的查询
     * */
    List<SpecialClassBO> selectSpecialClassBO();


}
