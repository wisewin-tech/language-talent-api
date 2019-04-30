package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 系统操作记录表
 */
public class SystemoperationBO extends BaseModel {

    private Integer id; //系统操作id
    private Integer adminId; //后台id
    private String content; //内容
    private String contenttype; //内容类型
    private String operationtype; //操作类型(增删改查)
    private Date soReleasetime; //操作时间

    public SystemoperationBO() {

    }

    public SystemoperationBO(Integer adminId, String content, String contenttype, String operationtype, Date soReleasetime) {
        this.adminId = adminId;
        this.content = content;
        this.contenttype = contenttype;
        this.operationtype = operationtype;
        this.soReleasetime = soReleasetime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    public void setSoReleasetime(Date soReleasetime) {
        this.soReleasetime = soReleasetime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public String getContent() {
        return content;
    }

    public String getContenttype() {
        return contenttype;
    }

    public String getOperationtype() {
        return operationtype;
    }

    public Date getSoReleasetime() {
        return soReleasetime;
    }
}
