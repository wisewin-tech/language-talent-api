package com.wisewin.api.entity.bo;



import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

/**
 * baaner表(首页)
 */
public class BannerBO extends BaseModel {
    private String title; //标题
    private Integer adminId; //后台管理员id
    private String pictureUrl; //图片url
    private String type; //类型
    private String skipUrl; //点击图片跳转
    private Date brReleasetime; //创建时间
    private Date brUsertime; //修改时间
    private String statusbaaner; //状态
    private String baanerrank; //排序



    public void setTitle(String title) {
        this.title = title;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public void setBrReleasetime(Date brReleasetime) {
        this.brReleasetime = brReleasetime;
    }

    public void setBrUsertime(Date brUsertime) {
        this.brUsertime = brUsertime;
    }

    public void setStatusbaaner(String statusbaaner) {
        this.statusbaaner = statusbaaner;
    }

    public void setBaanerrank(String baanerrank) {
        this.baanerrank = baanerrank;
    }

    public String getTitle() {
        return title;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getType() {
        return type;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public Date getBrReleasetime() {
        return brReleasetime;
    }

    public Date getBrUsertime() {
        return brUsertime;
    }

    public String getStatusbaaner() {
        return statusbaaner;
    }

    public String getBaanerrank() {
        return baanerrank;
    }
}
