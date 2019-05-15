package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

/**
 * Created by 王彬 on 2019/5/13.
 */
public class CourseIdName  extends BaseModel {
    private Integer coursesId;
    private String coursesName;

    public Integer getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Integer coursesId) {
        this.coursesId = coursesId;
    }

    public String getCoursesName() {
        return coursesName;
    }

    public void setCoursesName(String coursesName) {
        this.coursesName = coursesName;
    }
}
