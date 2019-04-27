package com.wisewin.api.service;

import com.wisewin.api.dao.CourseDAO;
import com.wisewin.api.entity.bo.CourseBO;
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
}
