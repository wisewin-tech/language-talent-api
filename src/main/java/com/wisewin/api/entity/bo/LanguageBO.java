package com.wisewin.api.entity.bo;


import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

public class LanguageBO extends BaseModel {
    private Integer id; //语言表
    private String languageName; //语言名称
    private String status; //状态
    private String foreignLanguageName; //外文名称
    private String ensignImageUrl; //国旗图片路径
    private String thumbnailImageUrl; //缩略图
    private Integer popularSort; //热门排序
    private String languageLightspot; //语言亮点
    private String purchaseNotes; //购买须知
    private String videoPath; //视频路径
    private String languageIntro; //语言简介
    private Integer languagePrice; //价格
    private Integer languageDiscountPrice; //特惠价
    private Date discountStartTime; //特惠开始时间
    private Date discountEndTime; //特惠结束时间
    private Long discountTimeRemaining;//特惠剩余时间
    private String courseName;//课程名称
    private Integer coursePrice;//课程价格
    private Integer courseDiscountPrice;//课程特惠价格
    private Integer chapterCount;//课时数
    private Integer levelId;//级别id
    private String levelName;//级别名称
    private Integer courseId;//课程id
    private String certificateOrNot;//是否可以考证
    private String certificateImage;//证书图片
    private Integer languageId;//语言id
    private String  certificateTitle;//证书标题

    public String getCertificateTitle() {
        return certificateTitle;
    }

    public void setCertificateTitle(String certificateTitle) {
        this.certificateTitle = certificateTitle;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getForeignLanguageName() {
        return foreignLanguageName;
    }

    public void setForeignLanguageName(String foreignLanguageName) {
        this.foreignLanguageName = foreignLanguageName;
    }

    public String getEnsignImageUrl() {
        return ensignImageUrl;
    }

    public void setEnsignImageUrl(String ensignImageUrl) {
        this.ensignImageUrl = ensignImageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public Integer getPopularSort() {
        return popularSort;
    }

    public void setPopularSort(Integer popularSort) {
        this.popularSort = popularSort;
    }

    public String getLanguageLightspot() {
        return languageLightspot;
    }

    public void setLanguageLightspot(String languageLightspot) {
        this.languageLightspot = languageLightspot;
    }

    public String getPurchaseNotes() {
        return purchaseNotes;
    }

    public void setPurchaseNotes(String purchaseNotes) {
        this.purchaseNotes = purchaseNotes;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getLanguageIntro() {
        return languageIntro;
    }

    public void setLanguageIntro(String languageIntro) {
        this.languageIntro = languageIntro;
    }

    public Integer getLanguagePrice() {
        return languagePrice;
    }

    public void setLanguagePrice(Integer languagePrice) {
        this.languagePrice = languagePrice;
    }

    public Integer getLanguageDiscountPrice() {
        return languageDiscountPrice;
    }

    public void setLanguageDiscountPrice(Integer languageDiscountPrice) {
        this.languageDiscountPrice = languageDiscountPrice;
    }

    public Date getDiscountStartTime() {
        return discountStartTime;
    }

    public void setDiscountStartTime(Date discountStartTime) {
        this.discountStartTime = discountStartTime;
    }

    public Date getDiscountEndTime() {
        return discountEndTime;
    }

    public void setDiscountEndTime(Date discountEndTime) {
        this.discountEndTime = discountEndTime;
    }

    public Long getDiscountTimeRemaining() {
        return discountTimeRemaining;
    }

    public void setDiscountTimeRemaining(Long discountTimeRemaining) {
        this.discountTimeRemaining = discountTimeRemaining;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getCourseDiscountPrice() {
        return courseDiscountPrice;
    }

    public void setCourseDiscountPrice(Integer courseDiscountPrice) {
        this.courseDiscountPrice = courseDiscountPrice;
    }

    public Integer getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(Integer chapterCount) {
        this.chapterCount = chapterCount;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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

    public String getCertificateOrNot() {
        return certificateOrNot;
    }

    public void setCertificateOrNot(String certificateOrNot) {
        this.certificateOrNot = certificateOrNot;
    }

    public String getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(String certificateImage) {
        this.certificateImage = certificateImage;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }
}
