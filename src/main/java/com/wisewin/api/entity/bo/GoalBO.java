package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;
import java.util.Date;

/**
 * 目的
 */
public class GoalBO extends BaseModel {

    private Integer Id; //目的id
    private String ppPurpose; //目的
    private Integer adminId; //创建人
    private Date ppReleasetime; //创建时间
    private Date ppUpdatetime; //修改时间

    public GoalBO(){}


    public GoalBO(String ppPurpose, Integer adminId, Date ppReleasetime, Date ppUpdatetime) {
        this.ppPurpose = ppPurpose;
        this.adminId = adminId;
        this.ppReleasetime = ppReleasetime;
        this.ppUpdatetime = ppUpdatetime;
    }

    public GoalBO(String ppPurpose, Integer adminId) {
        this.ppPurpose = ppPurpose;
        this.adminId = adminId;
    }

    public Integer getId() {
        return Id;
    }

    public String getPpPurpose() {
        return ppPurpose;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public Date getPpReleasetime() {
        return ppReleasetime;
    }

    public Date getPpUpdatetime() {
        return ppUpdatetime;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setPpPurpose(String ppPurpose) {
        this.ppPurpose = ppPurpose;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setPpReleasetime(Date ppReleasetime) {
        this.ppReleasetime = ppReleasetime;
    }

    public void setPpUpdatetime(Date ppUpdatetime) {
        this.ppUpdatetime = ppUpdatetime;
    }
}
