package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 合作机构表
 */
public class CollaborateBO extends BaseModel {
    private Integer id; //合作机构id
    private String cbName; //机构名字
    private Date cbReleasetime; // 创建时间
    private Date cbUpdatetime; // 修改时间

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCbName(String cbName) {
        this.cbName = cbName;
    }

    public void setCbReleasetime(Date cbReleasetime) {
        this.cbReleasetime = cbReleasetime;
    }

    public void setCbUpdatetime(Date cbUpdatetime) {
        this.cbUpdatetime = cbUpdatetime;
    }

    public Integer getId() {
        return id;
    }

    public String getCbName() {
        return cbName;
    }

    public Date getCbReleasetime() {
        return cbReleasetime;
    }

    public Date getCbUpdatetime() {
        return cbUpdatetime;
    }
}
