package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.CourseBO;
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
     * 课程详情
     * @return
     */
    List<CourseBO> courseDetails(Integer id);

    /**
     * 模糊查询课程列表
     * @param languageName
     * @return
     */
    List<CourseBO> courseSearch(String languageName);

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

}
