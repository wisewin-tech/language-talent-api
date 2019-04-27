package com.wisewin.api.entity.bo;




import com.wisewin.api.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class CourseBO extends BaseModel {
    private Integer id; //课程分类表
    private String courseName; //课程名称
    private String languageId; //语言id
    private String foreignName; //外国名字
    private String status; //状态
    private BigDecimal price; //价格
    private BigDecimal discountPrice; //特惠价
    private Date discountStartTime; //特惠开始时间
    private Date discountEndTime; //特惠结束时间
    private Integer studyNumber; //学习人数
    private String courseIntro; //课程简介
    private String courseLightspot; //课程亮点
    private String purchaseNotes; //购买须知
    private Integer popularSort; //热门排序
    private String hotOrNot; //是否为热门
    private String certificateOrNot;//是否可以考证
    private Integer certificateId;//证书id
    private Integer createUserId; //创建人id
    private Date createTime; //创建时间
    private Integer updateUserId; //修改人id
    private Date updateTime; //修改时间
    private String languageName;//语言名称
    private String thumbnailImageUrl;//国旗图片路径
    private Integer courseId;//课程id
    private String courseThumbnail;//缩略图
    private String certificateTitle;//证书标题
    private String certificateImageUrl;//证书图片地址
    private String certificateIntro;//证书简介
    private String levelName;//级别名称
    private Integer levelId;//级别id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
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

    public Integer getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(Integer studyNumber) {
        this.studyNumber = studyNumber;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public String getCourseLightspot() {
        return courseLightspot;
    }

    public void setCourseLightspot(String courseLightspot) {
        this.courseLightspot = courseLightspot;
    }

    public String getPurchaseNotes() {
        return purchaseNotes;
    }

    public void setPurchaseNotes(String purchaseNotes) {
        this.purchaseNotes = purchaseNotes;
    }

    public Integer getPopularSort() {
        return popularSort;
    }

    public void setPopularSort(Integer popularSort) {
        this.popularSort = popularSort;
    }

    public String getHotOrNot() {
        return hotOrNot;
    }

    public void setHotOrNot(String hotOrNot) {
        this.hotOrNot = hotOrNot;
    }

    public String getCertificateOrNot() {
        return certificateOrNot;
    }

    public void setCertificateOrNot(String certificateOrNot) {
        this.certificateOrNot = certificateOrNot;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
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

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseThumbnail() {
        return courseThumbnail;
    }

    public void setCourseThumbnail(String courseThumbnail) {
        this.courseThumbnail = courseThumbnail;
    }

    public String getCertificateTitle() {
        return certificateTitle;
    }

    public void setCertificateTitle(String certificateTitle) {
        this.certificateTitle = certificateTitle;
    }

    public String getCertificateImageUrl() {
        return certificateImageUrl;
    }

    public void setCertificateImageUrl(String certificateImageUrl) {
        this.certificateImageUrl = certificateImageUrl;
    }

    public String getCertificateIntro() {
        return certificateIntro;
    }

    public void setCertificateIntro(String certificateIntro) {
        this.certificateIntro = certificateIntro;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }
}
