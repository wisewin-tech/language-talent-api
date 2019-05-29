package com.wisewin.api.entity.bo;

/**
 * Created by 王彬 on 2019/5/29.
 */
public class CourseOrderBO {
    //课程缩略图
    private String thumbnailImageUrl;
    //课程简洁
    private String courseIntro;

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }
}
