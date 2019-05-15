package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

public class LanguageDetailsResultBO extends BaseModel {
    private Integer id; //语言表
    private String languageName; //语言名称
    private String languageLightspot; //语言亮点
    private String purchaseNotes; //购买须知
    private String videoPath; //视频路径
    private String languageIntro; //语言简介
    private Integer languagePrice; //价格
    private Integer languageDiscountPrice; //特惠价格

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
}
