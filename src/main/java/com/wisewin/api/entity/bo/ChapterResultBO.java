package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

public class ChapterResultBO extends BaseModel{
    private String chapterName; //课时名称
    private String freeOrNot; //是否免费观看
    private Integer chapterId;//课时id
    private String videoPath; //链接地址
    private String thumbnailUrl;//缩略图
    private Integer score;//分数
    private String didOrNot;//是否做过此课时试题

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getFreeOrNot() {
        return freeOrNot;
    }

    public void setFreeOrNot(String freeOrNot) {
        this.freeOrNot = freeOrNot;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getDidOrNot() {
        return didOrNot;
    }

    public void setDidOrNot(String didOrNot) {
        this.didOrNot = didOrNot;
    }
}
