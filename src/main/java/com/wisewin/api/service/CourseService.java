package com.wisewin.api.service;

import com.wisewin.api.dao.CourseDAO;
import com.wisewin.api.entity.bo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("courseService")
@Transactional
public class CourseService {
    @Resource
    private CourseDAO courseDAO;

    /**
     * 热门课程
     * @return
     */
    public List<CourseBO> getHotCourse(Map<String,Object> map){
        return courseDAO.getHotCourse(map);
    }

    /**
     * 课程详情课程模块
     * @return
     */
    public CourseDetailsResultBO courseDetailsCourse(Integer id){
        return courseDAO.courseDetailsCourse(id);
    }
    //课程详情级别模块
    public List<CourseDetailsLevelResultBO> courseDetailsLevel(Integer id){
        return courseDAO.courseDetailsLevel(id);
    }
    /**
     * 模糊查询课程列表
     * @param languageName
     * @return
     */
    public List<CourseDetailsResultBO> courseSearch(String languageName,String courseName){
        return courseDAO.courseSearch(languageName,courseName);
    }

    /**
     * 热门课程
     * @return
     */
    public List<HotCourseResultBO> hotCourse(){
        return courseDAO.hotCourse();
    }
}
