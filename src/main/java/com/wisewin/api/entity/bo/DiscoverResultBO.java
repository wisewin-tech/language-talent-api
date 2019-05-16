package com.wisewin.api.entity.bo;

import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

public class DiscoverResultBO {
    private Integer userId;         //用户id
    private String  source;        //收藏来源(课时,发现)
    private String  sourceId;    //收藏来源id'
    private String  title;        //发现标题
    private String  thumbnail;    //缩略图url
    private String  video;       //视频url
    private String  content;    //内容(新闻发现描述)
    private String  type;       //类型(新闻journalism 视频curriculum 线下活动activity)
    private String  activitytime;    //活动时间
    private String  ticket;       //在线购票价格double(20,0)
    private String  skip;       //跳转url
    private String  videoImg;   //存放视频封面url
    private Integer createId; //创建人id
    private Integer updateId; //修改人id
    private String createTime; //创建时间
    private String updateTime; //修改时间

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivitytime() {
        return activitytime;
    }

    public void setActivitytime(String activitytime) {
        this.activitytime = activitytime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public String getCreateTime() {
        return DateUtil.getStr(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = DateUtil.getStr(createTime);
    }

    public String getUpdateTime() {
        return DateUtil.getStr(updateTime);
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = DateUtil.getStr(updateTime);
    }
}
