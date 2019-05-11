package com.wisewin.api.entity.bo;

import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class FavoritesResultBO {
    private Integer userId;         //用户id
    private String  languageName;  //语言名称
    private String  courseName;    //课程名称
    private String  levelName;     //级别名称
    private String  chapterName;   //课时名称
    private String  thumbnailUrl;   //缩略图
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
                ", levelName='" + levelName + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", createId=" + createId +
                ", updateId=" + updateId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
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
        return DateUtil.getStr(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = DateUtil.getStr(createTime);
    }

    public String getUpdateTime() {
        return DateUtil.getStr(updateTime);
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = DateUtil.getStr(updateTime);
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
