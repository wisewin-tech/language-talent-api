package com.wisewin.api.entity.dto;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 感兴趣的语言表
 */
public class CareahangDTO extends BaseModel {

    private Integer id; //感兴趣的语言id
    private Integer languageId; //语言表id
    private Integer userId; //用户id
    private Date chReleasetime; //创建时间

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setChReleasetime(Date chReleasetime) {
        this.chReleasetime = chReleasetime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getChReleasetime() {
        return chReleasetime;
    }
}
