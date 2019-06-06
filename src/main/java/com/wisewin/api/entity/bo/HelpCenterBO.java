package com.wisewin.api.entity.bo;


import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

public class HelpCenterBO extends BaseModel {
    private Integer id; //帮助中心
    private String title; //标题
    private String content; //内容
    private Integer serialNumber; //序号码

    @Override
    public String toString() {
        return "HelpCenterBO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }


}
