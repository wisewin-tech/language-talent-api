package com.wisewin.api.entity.bo;

import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class FavoritesResultBO {
    private Integer userId;         //用户id
    private String  languageName;  //语言名称
    private String  courseName;    //课程名称
    private String courseId;//课程id
    private String  levelName;     //级别名称
    private String  chapterName;   //课时名称
    private String chapterId;//课时id
    private String  thumbnailUrl;   //缩略图
    private String  chapterIntro;   //课时简介
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private String createTime; //创建时间
    private String updateTime; //修改时间


    @Override
    public String toString() {
        return "FavoritesResultBO{" +
                "userId=" + userId +
                ", languageName='" + languageName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseId='" + courseId + '\'' +
                ", levelName='" + levelName + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", chapterIntro='" + chapterIntro + '\'' +
                ", createId=" + createId +
                ", updateId=" + updateId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getChapterIntro() {
        return chapterIntro;
    }

    public void setChapterIntro(String chapterIntro) {
        this.chapterIntro = chapterIntro;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
