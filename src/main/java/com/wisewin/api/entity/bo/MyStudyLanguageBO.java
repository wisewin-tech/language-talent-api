package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

public class MyStudyLanguageBO extends BaseModel {
    private Integer languageId;//语言id
    private String ensignImageUrl;//语言国旗图片
    private String languageName;//语言名称

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

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
}
