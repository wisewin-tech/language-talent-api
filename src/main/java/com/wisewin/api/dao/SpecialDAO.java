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
    List<SpecialBO> selectSpecialBO(@Param("classId") Integer classId);

    /**
     * 某个用户点的某个专题的查询
     * */
    SpecialBO selectSpecialBOById(@Param("id") Integer id);

    /**
     * 访问专题了 专题访问数 +1
     * */
    Integer addSpecialTraffic(@Param("id")Integer id);


    /**
     * 查看某个人 是否喜欢了这个专题
     * */
    Integer checkUserLikeSpecial(@Param("userId")Integer userId,@Param("specialId")Integer specialId);

    /**
     * 把这个专题点为喜欢
     * */
    Integer addSpecialLikeUser(@Param("userId")Integer userId,@Param("specialId")Integer specialId);

    /**
     * 取消对这个专题的喜欢
     * */
    Integer delSpecialLikeUser(@Param("userId")Integer userId,@Param("specialId")Integer specialId);

    /**
     * 专题的喜欢数 +1 -1
     * */
    Integer updSpecialLikeCount(@Param("number")Integer number,@Param("specialId")Integer specialId);


    /**
     * 专题下 要展示出这个专题分类下的其他专题 如果没有就展示其他专题分类的专题
     * */



}
