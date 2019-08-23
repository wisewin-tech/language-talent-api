package com.wisewin.api.entity.bo;

import java.util.List;

public class CourseCerBO {
    private Integer courseId;
    private String courseName;
    private String describe;
    private String certificateIimage;
    private List<LevelCerBO> levels;


    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCertificateIimage() {
        return certificateIimage;
    }

    public void setCertificateIimage(String certificateIimage) {
        this.certificateIimage = certificateIimage;
    }

    public List<LevelCerBO> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelCerBO> levels) {
        this.levels = levels;
    }
}
