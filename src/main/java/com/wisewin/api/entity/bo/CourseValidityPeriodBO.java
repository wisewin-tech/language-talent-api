
package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

/**
 * Created by 王彬 on 2019/5/31.
 */
public class CourseValidityPeriodBO extends BaseModel {
    private  String courseValidityPeriod;

    public String getCourseValidityPeriod() {
        return courseValidityPeriod;
    }

    public void setCourseValidityPeriod(String courseValidityPeriod) {
        this.courseValidityPeriod = courseValidityPeriod;
    }
}