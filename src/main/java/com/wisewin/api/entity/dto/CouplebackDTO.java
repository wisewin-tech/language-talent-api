package com.wisewin.api.entity.dto;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 意见反馈表
 */
public class CouplebackDTO extends BaseModel {

    private Integer id; //意见反馈id
    private Integer userId; //用户id
    private String content; //反馈内容
    private String contactpattern; //用户联系方式
    private String pictureUrl; //图片url
    private Date cbReleasetime; //创建时间
    private Date cbUpdatetime; //修改时间
    private String disposeresUlt; //处理结果
    private String disposeperson; //处理人



    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContactpattern(String contactpattern) {
        this.contactpattern = contactpattern;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setCbReleasetime(Date cbReleasetime) {
        this.cbReleasetime = cbReleasetime;
    }

    public void setCbUpdatetime(Date cbUpdatetime) {
        this.cbUpdatetime = cbUpdatetime;
    }

    public void setDisposeresUlt(String disposeresUlt) {
        this.disposeresUlt = disposeresUlt;
    }

    public void setDisposeperson(String disposeperson) {
        this.disposeperson = disposeperson;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getContactpattern() {
        return contactpattern;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Date getCbReleasetime() {
        return cbReleasetime;
    }

    public Date getCbUpdatetime() {
        return cbUpdatetime;
    }

    public String getDisposeresUlt() {
        return disposeresUlt;
    }

    public String getDisposeperson() {
        return disposeperson;
    }
}
