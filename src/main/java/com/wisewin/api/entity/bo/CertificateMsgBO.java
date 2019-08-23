package com.wisewin.api.entity.bo;

import java.util.List;

public class CertificateMsgBO {
    private Integer languageId;
    private String imageUrl;
    private String languageName;
    private List<CourseCerBO>  courses;


    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public List<CourseCerBO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseCerBO> courses) {
        this.courses = courses;
    }


}
