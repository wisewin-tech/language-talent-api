package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.SpecialBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专题
 * */
public interface SpecialDAO {

    /**
     * 查询所有状态正常专题
     * */
    List<SpecialBO> selectSpecialBO();

    /**
     * 把这个专题点为喜欢 类似于点赞 涉及到不能重复喜欢
     * */

    /**
     * 专题下 要展示出这个专题分类下的其他专题 如果没有就展示其他专题分类的专题
     * */


}
