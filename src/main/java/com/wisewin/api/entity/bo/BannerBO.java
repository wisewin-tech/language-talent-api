package com.wisewin.api.entity.bo;



import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

/**
 * baaner表(首页)
 */
public class BannerBO extends BaseModel {
    private Integer id; //baaner表(首页)id
    private String title; //标题
    private String pictureUrl; //图片url
    private String type; //类型
    private String skipUrl; //点击图片跳转
    private String sort; //排序
    private String status; //状态


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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
