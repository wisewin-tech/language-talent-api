package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.CourseBO;

import java.util.List;

public interface CourseDAO {
    /**
     * 热门课程
     * @return
     */
    List<CourseBO> getHotCourse();

}
