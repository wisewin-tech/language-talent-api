package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class UserStudyDetailsBO extends BaseModel{
    private Integer id; //学习详情表
    private Integer userId; //用户id
    private Date studyDate; //学习日期
    private Integer studyDuration; //学习时长(分钟)

    @Override
    public String toString() {
        return "UserStudyDetailsBO{" +
                "id=" + id +
                ", userId=" + userId +
                ", studyDate=" + studyDate +
                ", studyDuration=" + studyDuration +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStudyDate() {
        String dateStr = DateUtil.getDateStr(this.studyDate);
        return dateStr;
    }

    public void setStudyDate(Date studyDate) {
        this.studyDate = studyDate;
    }

    public Integer getStudyDuration() {
        return studyDuration;
    }

    public void setStudyDuration(Integer studyDuration) {
        this.studyDuration = studyDuration;
    }
}
