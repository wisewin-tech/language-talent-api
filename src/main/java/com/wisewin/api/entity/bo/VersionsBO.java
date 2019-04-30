package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 版本表
 */
public class VersionsBO extends BaseModel {
    private Integer id; //版本id
    private Integer versionsnum; //发版次数
    private String model; //版本号
    private String content; //内容
    private Integer adminId; //后台管理员id
    private Date vsReleasetime; //创建时间
    private Integer vsUpdateadminId; //后台管理员修改id
    private Date vsUpdatetime; //修改时间
    private String compatibility; //兼容版本



    public void setId(Integer id) {
        this.id = id;
    }

    public void setVersionsnum(Integer versionsnum) {
        this.versionsnum = versionsnum;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setVsReleasetime(Date vsReleasetime) {
        this.vsReleasetime = vsReleasetime;
    }

    public void setVsUpdateadminId(Integer vsUpdateadminId) {
        this.vsUpdateadminId = vsUpdateadminId;
    }

    public void setVsUpdatetime(Date vsUpdatetime) {
        this.vsUpdatetime = vsUpdatetime;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public Integer getId() {
        return id;
    }

    public Integer getVersionsnum() {
        return versionsnum;
    }

    public String getModel() {
        return model;
    }

    public String getContent() {
        return content;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public Date getVsReleasetime() {
        return vsReleasetime;
    }

    public Integer getVsUpdateadminId() {
        return vsUpdateadminId;
    }

    public Date getVsUpdatetime() {
        return vsUpdatetime;
    }

    public String getCompatibility() {
        return compatibility;
    }
}
