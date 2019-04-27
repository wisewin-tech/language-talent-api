package com.wisewin.api.service;

import com.wisewin.api.dao.CourseDAO;
import com.wisewin.api.entity.bo.CourseBO;
import com.wisewin.api.entity.bo.LanguageBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("courseService")
@Transactional
public class CourseService {
    @Resource
    private CourseDAO courseDAO;

    /**
     * 热门课程
     * @return
     */
    public List<CourseBO> getHotCourse(){
        return courseDAO.getHotCourse();
    }

    /**
     * 课程详情
     * @return
     */
    public List<CourseBO> courseDetails(CourseBO id){
        return courseDAO.courseDetails(id);
    }
    /**
     * 模糊查询课程列表
     * @param languageName
     * @return
     */
    public List<CourseBO> courseSearch(String languageName){
        return courseDAO.courseSearch(languageName);
    }
}
