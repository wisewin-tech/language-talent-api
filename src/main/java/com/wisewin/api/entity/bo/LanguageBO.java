package com.wisewin.api.entity.bo;


import com.wisewin.api.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class LanguageBO extends BaseModel {
    private Integer id; //语言表
    private String languageName; //语言名称
    private String status; //状态
    private String foreignLanguageName; //外文名称
    private String ensignImageUrl; //国旗图片路径
    private String thumbnailImageUrl; //
    private Integer popularSort; //热门排序
    private String languageLightspot; //语言亮点
    private String purchaseNotes; //购买须知
    private String videoPath; //视频路径
    private String languageIntro; //语言简介
    private BigDecimal languagePrice; //价格
    private BigDecimal languageDiscountPrice; //特惠价
    private Date discountStartTime; //特惠开始时间
    private Date discountEndTime; //特惠结束时
    private String courseName;//课程名称
    private BigDecimal coursePrice;//课程价格
    private BigDecimal courseDiscountPrice;//课程特惠价格
    private Integer chapterCount;//课时数
    private Integer levelId;//级别id
    private String levelName;//级别名称
    private Integer createUserId; //创建人id
    private Date createTime; //创建时间
    private Integer updateUserId; //修改人id
    private Date updateTime; //修改时间
    private Integer courseId;//课程id
    private String certificateOrNot;//是否可以考证
    private Integer certificateId;//证书id
    private String certificateImage;//证书图片

    public String getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(String certificateImage) {
        this.certificateImage = certificateImage;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateOrNot() {
        return certificateOrNot;
    }

    public void setCertificateOrNot(String certificateOrNot) {
        this.certificateOrNot = certificateOrNot;
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

    public BigDecimal getLanguagePrice() {
        return languagePrice;
    }

    public void setLanguagePrice(BigDecimal languagePrice) {
        this.languagePrice = languagePrice;
    }

    public BigDecimal getLanguageDiscountPrice() {
        return languageDiscountPrice;
    }

    public void setLanguageDiscountPrice(BigDecimal languageDiscountPrice) {
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(BigDecimal coursePrice) {
        this.coursePrice = coursePrice;
    }

    public BigDecimal getCourseDiscountPrice() {
        return courseDiscountPrice;
    }

    public void setCourseDiscountPrice(BigDecimal courseDiscountPrice) {
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
