package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class FlashSalesResultBO extends BaseModel{
    private String thumbnailImageUrl; //缩略图
    private String languageName; //语言名称
    private String languageIntro; //语言简介
    private Integer languagePrice; //价格
    private Integer languageDiscountPrice; //特惠价格
    private String discountEndTime; //特惠结束时间
    private Long discountTimeRemaining;//特惠剩余时间
    private String certificateOrNot;//是否可以考证

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
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

    public String getDiscountEndTime() {
        return DateUtil.getGainStr(discountEndTime);
    }

    public void setDiscountEndTime(String discountEndTime) {
        this.discountEndTime = DateUtil.getGainStr(discountEndTime);
    }

    public Long getDiscountTimeRemaining() {
        return discountTimeRemaining;
    }

    public void setDiscountTimeRemaining(Long discountTimeRemaining) {
        this.discountTimeRemaining = discountTimeRemaining;
    }

    public String getCertificateOrNot() {
        return certificateOrNot;
    }

    public void setCertificateOrNot(String certificateOrNot) {
        this.certificateOrNot = certificateOrNot;
    }
}
