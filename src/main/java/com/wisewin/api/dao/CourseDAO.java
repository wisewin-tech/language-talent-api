package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CourseDAO {
    /**
     * 热门课程
     * @return
     */
    List<CourseBO> getHotCourse(Map<String,Object> map);

    /**
     * 课程详情课程模块
     * @return
     */
    CourseDetailsResultBO courseDetailsCourse(Integer id);
    //课程详情级别模块
    List<CourseDetailsLevelResultBO> courseDetailsLevel(Integer id);


    /**
     * 模糊查询课程列表
     * @param languageName
     * @return
     */
    List<CourseDetailsResultBO> courseSearch(@Param("languageName") String languageName);

    /**
     * 单查询课程
     */
    CourseBO selectCourse(@Param("id") String id);


    /**
     * 根据语言id查询课程
     * @param id
     * @return
     */
    List<CourseBO>  listCousebyLanguage(@Param("id") String id);

    /**
     * 热门课程
     * @return
     */
    List<HotCourseResultBO> hotCourse();
}
