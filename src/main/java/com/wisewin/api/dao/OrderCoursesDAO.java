package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.OrderCoursesBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 王彬 on 2019/5/9.
 */
public interface OrderCoursesDAO {
    /**
     * 查询当前用户是否购买当前课程
     */
    OrderCoursesBO selectOrderCourses(@Param("userId")Integer userId, @Param("coursesId")String coursesId);

    /**
     * 插入
     */
    void insetOrderCourse(OrderCoursesBO orderCourses);

    /**
     * 批量插入
     */
    void insetListOrderCourse(List<OrderCoursesBO> list);

    /**
     * 添加多条子课程
     */
    Integer addCourses(@Param("orderCoursesBOS") List<OrderCoursesBO> orderCoursesBOS);

}
