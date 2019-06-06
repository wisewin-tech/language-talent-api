package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.List;

public class LanguageResultBO extends BaseModel {
    private Integer id; //语言表
    private String languageName; //语言名称
    private List<CourseResultBO> courseList;//课程集合

    @Override
    public String toString() {
        return "LanguageResultBO{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                ", courseList=" + courseList +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public List<CourseResultBO> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseResultBO> courseList) {
        this.courseList = courseList;
    }
}
