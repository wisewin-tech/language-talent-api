package com.wisewin.api.entity.bo;

import java.util.Date;

/**
 * 邀请记录
 */
public class InviteRecordBO {
    private Integer id;
    private Integer inviteUserId;//邀请人id
    private Integer byUserId;//被邀请人id
    private Date createTime;//创建时间
    private Integer inviteCurrent;//邀请人获得咖豆
    private Integer byCurrent;//被邀请人获得咖豆


    public InviteRecordBO() {
    }


    public InviteRecordBO(Integer inviteUserId, Integer byUserId, Integer inviteCurrent, Integer byCurrent) {
        this.inviteUserId = inviteUserId;
        this.byUserId = byUserId;
        this.inviteCurrent = inviteCurrent;
        this.byCurrent = byCurrent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public Integer getByUserId() {
        return byUserId;
    }

    public void setByUserId(Integer byUserId) {
        this.byUserId = byUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getInviteCurrent() {
        return inviteCurrent;
    }

    public void setInviteCurrent(Integer inviteCurrent) {
        this.inviteCurrent = inviteCurrent;
    }

    public Integer getByCurrent() {
        return byCurrent;
    }

    public void setByCurrent(Integer byCurrent) {
        this.byCurrent = byCurrent;
    }


    @Override
    public String toString() {
        return "InviteRecordBO{" +
                "id=" + id +
                ", inviteUserId=" + inviteUserId +
                ", byUserId=" + byUserId +
                ", createTime=" + createTime +
                ", inviteCurrent=" + inviteCurrent +
                ", byCurrent=" + byCurrent +
                '}';
    }
}
