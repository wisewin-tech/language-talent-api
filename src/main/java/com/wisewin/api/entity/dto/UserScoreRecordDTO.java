package com.wisewin.api.entity.dto;

import java.util.Date;

public class UserScoreRecordDTO {
    private Integer id; //用户分数记录表
    private Integer userId; //用户id
    private Integer chapterId; //课时id
    private Integer score; //分数
    private Date doExerciseTime; //做题时间
    private String writeAnswer; //用户做题答案
    private Integer createUserId; //创建人id
    private Date createTime; //创建时间
    private Integer updateUserId; //修改人id
    private Date updateTime; //修改时间

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

    public Date getDoExerciseTime() {
        return doExerciseTime;
    }

    public void setDoExerciseTime(Date doExerciseTime) {
        this.doExerciseTime = doExerciseTime;
    }

    public String getWriteAnswer() {
        return writeAnswer;
    }

    public void setWriteAnswer(String writeAnswer) {
        this.writeAnswer = writeAnswer;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
