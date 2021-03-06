package com.wisewin.api.entity.param;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import com.wisewin.api.util.date.DateUtil;

import java.util.Date;

/**
 * Created by yyh on 2019/4/24* 发现实体类
 */
public class DiscoverParam extends BaseModel {

    private Integer id; //发现id
    private String title; //发现标题
    private Integer browse; //浏览人数
    private String dcName; //创建人
    private Date createTime; //发布时间
    private String dcUpdatename; //最后修改人
    private String updateTime; //最后修改时间
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
    private  String way; // 展示方式
    private  String videoImg;//存放视频封面url
    private Integer pageNo; //页数
    private  Integer pageSize; //每条条数
    private int countnum; // 总数
    private String description; //内容描述
    private String source;//访问来源国

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "DiscoverParam{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", browse=" + browse +
                ", dcName='" + dcName + '\'' +
                ", createTime=" + createTime +
                ", dcUpdatename='" + dcUpdatename + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", video='" + video + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", likenum=" + likenum +
                ", participation=" + participation +
                ", activitytime=" + activitytime +
                ", activitysite='" + activitysite + '\'' +
                ", phone='" + phone + '\'' +
                ", ticket=" + ticket +
                ", priority=" + priority +
                ", stick='" + stick + '\'' +
                ", show='" + show + '\'' +
                ", skip='" + skip + '\'' +
                ", way='" + way + '\'' +
                ", videoImg='" + videoImg + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", countnum=" + countnum +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public int getCountnum() {
        return countnum;
    }

    public void setCountnum(int countnum) {
        this.countnum = countnum;
    }

    public String getCreateTime() {
        return   DateUtil.getDateStr(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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
        return   DateUtil.getDateStr(activitytime);

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

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
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

    public void setSkip(String skip) {
        this.skip = skip;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
