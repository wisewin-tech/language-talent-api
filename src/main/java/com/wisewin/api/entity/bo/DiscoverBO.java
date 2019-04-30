package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * Created by yyh on 2019/4/24* 发现实体类
 */
public class DiscoverBO extends BaseModel {

    private Integer dc_id; //发现id
    private String title; //发现标题
    private Integer browse; //浏览人数
    private String dcName; //创建人
    private Date dcReleasetime; //发布时间
    private String dcUpdatename; //最后修改人
    private Date dcUpdatetime; //最后修改时间
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
    private String priority; //优先级
    private String stick; //置顶[是与否]
    private String show; //是否显示
    private String skip; //跳转url[线下活动]

    public  DiscoverBO(){}


    public DiscoverBO( String title, Integer browse, String dcName, String dcUpdatename, String thumbnail, String video, String content, String type, Integer likenum, Integer participation, Date activitytime, String activitysite, String phone, double ticket, String priority, String stick, String show, String skip) {

        this.title = title;
        this.browse = browse;
        this.dcName = dcName;
        this.dcUpdatename = dcUpdatename;
        this.thumbnail = thumbnail;
        this.video = video;
        this.content = content;
        this.type = type;
        this.likenum = likenum;
        this.participation = participation;
        this.activitytime = activitytime;
        this.activitysite = activitysite;
        this.phone = phone;
        this.ticket = ticket;
        this.priority = priority;
        this.stick = stick;
        this.show = show;
        this.skip = skip;
    }

    public DiscoverBO(String title, Integer browse, String dcName, Date dcReleasetime, String dcUpdatename, Date dcUpdatetime, String thumbnail, String video, String content, String type, Integer likenum, Integer participation, Date activitytime, String activitysite, String phone, double ticket, String priority, String stick, String show, String skip) {
        this.title = title;
        this.browse = browse;
        this.dcName = dcName;
        this.dcReleasetime = dcReleasetime;
        this.dcUpdatename = dcUpdatename;
        this.dcUpdatetime = dcUpdatetime;
        this.thumbnail = thumbnail;
        this.video = video;
        this.content = content;
        this.type = type;
        this.likenum = likenum;
        this.participation = participation;
        this.activitytime = activitytime;
        this.activitysite = activitysite;
        this.phone = phone;
        this.ticket = ticket;
        this.priority = priority;
        this.stick = stick;
        this.show = show;
        this.skip = skip;
    }

    public Integer getDc_id() {
        return dc_id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getBrowse() {
        return browse;
    }

    public String getDcName() {
        return dcName;
    }

    public Date getDcReleasetime() {
        return dcReleasetime;
    }

    public String getDcUpdatename() {
        return dcUpdatename;
    }

    public Date getDcUpdatetime() {
        return dcUpdatetime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public Integer getParticipation() {
        return participation;
    }

    public Date getActivitytime() {
        return activitytime;
    }

    public String getActivitysite() {
        return activitysite;
    }

    public String getPhone() {
        return phone;
    }

    public double getTicket() {
        return ticket;
    }

    public String getPriority() {
        return priority;
    }

    public String getStick() {
        return stick;
    }

    public String getShow() {
        return show;
    }

    public String getSkip() {
        return skip;
    }

    public void setDc_id(Integer dc_id) {
        this.dc_id = dc_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBrowse(Integer browse) {
        this.browse = browse;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public void setDcReleasetime(Date dcReleasetime) {
        this.dcReleasetime = dcReleasetime;
    }

    public void setDcUpdatename(String dcUpdatename) {
        this.dcUpdatename = dcUpdatename;
    }

    public void setDcUpdatetime(Date dcUpdatetime) {
        this.dcUpdatetime = dcUpdatetime;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public void setParticipation(Integer participation) {
        this.participation = participation;
    }

    public void setActivitytime(Date activitytime) {
        this.activitytime = activitytime;
    }

    public void setActivitysite(String activitysite) {
        this.activitysite = activitysite;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTicket(double ticket) {
        this.ticket = ticket;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }
}
