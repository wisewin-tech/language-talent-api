package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

public class QCertificateBO extends BaseModel{
    private Integer id;
    private String  name;
    private String beStudying;


    public String getBeStudying() {
        return beStudying;
    }

    public void setBeStudying(String beStudying) {
        this.beStudying = beStudying;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "QCertificateBO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
