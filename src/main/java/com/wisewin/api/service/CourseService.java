package com.wisewin.api.service;

import com.wisewin.api.dao.CourseDAO;
import com.wisewin.api.entity.bo.*;
import org.bouncycastle.crypto.DSA;
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
    public List<CourseDetailsResultBO> courseSearch(String languageName){
        return courseDAO.courseSearch(languageName);
    }

    /**
     * 热门课程
     * @return
     */
    public List<HotCourseResultBO> hotCourse(){
        return courseDAO.hotCourse();
    }
    /**
     * 通过语言id查课程id
     * @param languageId
     * @return
     */
    public List<CourseBO> getCourseIdById(Integer languageId){
        return courseDAO.getCourseIdById(languageId);
    }

    /**
     * 通过级别id查课程id
     * @param levelId
     * @return
     */
    public Integer getCourseIdByLevelId(Integer levelId){
        return courseDAO.getCourseIdByLevelId(levelId);
    }


}
