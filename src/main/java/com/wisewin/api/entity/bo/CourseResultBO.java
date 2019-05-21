package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.List;

public class CourseResultBO extends BaseModel {
    private Integer courseId;//课程id
    private String courseName;//课程名称
    private List<LevelResultBO> levelList;//级别集合

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<LevelResultBO> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<LevelResultBO> levelList) {
        this.levelList = levelList;
    }
}
