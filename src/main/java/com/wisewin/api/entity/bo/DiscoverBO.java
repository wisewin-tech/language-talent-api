package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import com.wisewin.api.util.DateUtils;

import java.util.Date;

/**
 * Created by yyh on 2019/4/24* 发现实体类
 */
public class DiscoverBO extends BaseModel {

    private Integer id; //发现id
    private String title; //发现标题
    private Integer browse; //浏览人数
    private String dcName; //创建人
    private Date createTime; //发布时间
    private String dcUpdatename; //最后修改人
    private Date updateTime; //最后修改时间
    private String thumbnail; //缩略图url
    private String video; //视频url
    private String content; //内容
    private String type; //类型](课程,新闻,线下活动)
    private Integer likenum; //喜欢人数
    private Integer participation; //要参与
    private Date activitytime; //活动时间
    private String activitysite; //活动地址
    private String phone; //联系电话
    private double ticket; //在线购票
    private double priority; //优先级
    private String stick; //置顶[是与否]
    private String show; //是否显示
    private String skip; //跳转url[线下活动]
    private String way; //展示方式
    private String videoImg;//存放视频封面url


    public DiscoverBO(Integer id, String title, Integer browse, Date createTime, String video, String content, Integer likenum) {
        this.id = id;
        this.title = title;
        this.browse = browse;
        this.createTime = createTime;
        this.video = video;
        this.content = content;
        this.likenum = likenum;
    }

    public DiscoverBO(Integer id, String title, Integer browse, Date createTime, String thumbnail, String video,String content, String type, Integer likenum, Integer participation, Date activitytime, String activitysite, String phone, double ticket, String skip,String videoImg) {
        this.id = id;
        this.title = title;
        this.browse = browse;
        this.createTime = createTime;
        this.thumbnail = thumbnail;
        this.video=video;
        this.content = content;
        this.type = type;
        this.likenum = likenum;
        this.participation = participation;
        this.activitytime = activitytime;
        this.activitysite = activitysite;
        this.phone = phone;
        this.ticket = ticket;
        this.skip = skip;
        this.videoImg=videoImg;
    }


    public DiscoverBO(Integer id, String title, Integer browse, Date createTime, String thumbnail, String video, String type, double priority, String stick, String show) {
        this.id = id;
        this.title = title;
        this.browse = browse;
        this.createTime = createTime;
        this.thumbnail = thumbnail;
        this.video = video;
        this.type = type;
        this.priority = priority;
        this.stick = stick;
        this.show = show;
    }


    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public  DiscoverBO(){}

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getCreateTime() {
        return DateUtils.getDateStr(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBrowse() {
        return browse;
    }

    public void setBrowse(Integer browse) {
        this.browse = browse;
    }

    public String getDcName() {
        return dcName;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public String getDcUpdatename() {
        return dcUpdatename;
    }

    public void setDcUpdatename(String dcUpdatename) {
        this.dcUpdatename = dcUpdatename;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

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

    public Integer getLikenum() {
        if(likenum==null){
            return 0;
        }
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Integer getParticipation() {
        return participation;
    }

    public void setParticipation(Integer participation) {
        this.participation = participation;
    }

    public String getActivitytime() {
        return DateUtils.getDateStr(activitytime);
    }

    public void setActivitytime(Date activitytime) {
        this.activitytime = activitytime;

    }

    public String getActivitysite() {
        return activitysite;
    }

    public void setActivitysite(String activitysite) {
        this.activitysite = activitysite;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTicket() {
        return ticket;
    }

    public void setTicket(double ticket) {
        this.ticket = ticket;
    }



    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStick() {
        return stick;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getSkip() {
        return skip;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }
}
