package com.wisewin.api.entity.param;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

public class LikerelationParam  {

    private Integer Id; //喜欢关系id
    private Integer userId; //用户id
    private Integer dcId; //发现id
    private Date lkReleasetime; //创建时间
    private Integer likenum; //喜欢

    public LikerelationParam(){}


    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setDcId(Integer dcId) {
        this.dcId = dcId;
    }

    public void setLkReleasetime(Date lkReleasetime) {
        this.lkReleasetime = lkReleasetime;
    }



    public Integer getUserId() {
        return userId;
    }

    public Integer getDcId() {
        return dcId;
    }

    public Date getLkReleasetime() {
        return lkReleasetime;
    }
}
