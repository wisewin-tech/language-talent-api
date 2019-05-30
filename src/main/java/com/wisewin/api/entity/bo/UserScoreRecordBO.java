package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

public class UserScoreRecordBO extends BaseModel{
    private Integer id; //用户分数记录表
    private Integer userId; //用户id
    private Integer chapterId; //课时id
    private Integer score; //分数



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

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


}
