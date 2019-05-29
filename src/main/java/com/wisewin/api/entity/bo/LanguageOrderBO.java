package com.wisewin.api.entity.bo;

/**
 * Created by 王彬 on 2019/5/29.
 */
public class LanguageOrderBO {
    //语言缩略图
    private String thumbnailImageUrl;
    //课程简介
    private String languageIntro;

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getLanguageIntro() {
        return languageIntro;
    }

    public void setLanguageIntro(String languageIntro) {
        this.languageIntro = languageIntro;
    }
}
