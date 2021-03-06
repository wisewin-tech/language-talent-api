package com.wisewin.api.entity.bo;



import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 字典表
 */
public class DictionariesBO extends BaseModel {

    private Integer id; //字典id
    private String key; //类型名字
    private String value; //类型
    private Integer dnId; //外连接,连接自己的
    private String dnName; //创建人
    private Date dnReleasetime; //发布时间
    private Integer updateUserId; //修改用户id
    private Double rank; //排序
    private String outer; //连接字典类型表valuename

    public  DictionariesBO(){}

    public DictionariesBO(String outer) {
        this.outer = outer;
    }

    public DictionariesBO(Integer id, String key, String value, String outer) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.outer = outer;
    }

    public Double getRank() {
        return rank;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

    public String getOuter() {
        return outer;
    }

    public void setOuter(String outer) {
        this.outer = outer;
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Integer getDnId() {
        return dnId;
    }

    public String getDnName() {
        return dnName;
    }

    public Date getDnReleasetime() {
        return dnReleasetime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDnId(Integer dnId) {
        this.dnId = dnId;
    }

    public void setDnName(String dnName) {
        this.dnName = dnName;
    }

    public void setDnReleasetime(Date dnReleasetime) {
        this.dnReleasetime = dnReleasetime;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}
