package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;

import java.util.List;

public interface CourseDAO {
    /**
     * 热门课程
     * @return
     */
    List<CourseBO> getHotCourse();

    /**
     * 课程详情
     * @return
     */
    List<CourseBO> courseDetails(CourseBO id);

    /**
     * 模糊查询课程列表
     * @param languageName
     * @return
     */
    List<CourseBO> courseSearch(String languageName);
}
