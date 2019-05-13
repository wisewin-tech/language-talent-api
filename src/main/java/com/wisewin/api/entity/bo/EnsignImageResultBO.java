package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

public class EnsignImageResultBO extends BaseModel {
    private String ensignImageUrl; //国旗图片路径
    private String languageName; //语言名称
    private Integer languageId;//语言id

    public String getEnsignImageUrl() {
        return ensignImageUrl;
    }

    public void setEnsignImageUrl(String ensignImageUrl) {
        this.ensignImageUrl = ensignImageUrl;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }
}
