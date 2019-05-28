package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

import java.util.Date;
import java.util.List;

public class LevelBO extends BaseModel {
    private Integer id; //级别表
    private String levelName; //级别名称
    private Integer courseId; //课程id
    private String status;//状态
    private String levelIntro; //简介
    private Integer serialNumber; //序号
    private String medalImageUrl;//勋章图片地址
    private String medalName;//勋章名称
    private String languageName;//语言名称
    private String ensignImageUrl;//语言国旗图片地址
    private String courseName;//课程名称
    private String courseIntro;//课程简介
    private String thumbnailUrl;//缩略图
    private Integer languageId;//语言id
    private String buyOrNot;//是否购买
    private List<ChapterResultBO> chapterBOList;

    public String getBuyOrNot() {
        return buyOrNot;
    }

    public void setBuyOrNot(String buyOrNot) {
        this.buyOrNot = buyOrNot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevelIntro() {
        return levelIntro;
    }

    public void setLevelIntro(String levelIntro) {
        this.levelIntro = levelIntro;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMedalImageUrl() {
        return medalImageUrl;
    }

    public void setMedalImageUrl(String medalImageUrl) {
        this.medalImageUrl = medalImageUrl;
    }

    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName;
    }


    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getEnsignImageUrl() {
        return ensignImageUrl;
    }

    public void setEnsignImageUrl(String ensignImageUrl) {
        this.ensignImageUrl = ensignImageUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public List<ChapterResultBO> getChapterBOList() {
        return chapterBOList;
    }

    public void setChapterBOList(List<ChapterResultBO> chapterBOList) {
        this.chapterBOList = chapterBOList;
    }
}
