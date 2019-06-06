package com.wisewin.api.entity.bo;


import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

public class AboutUsBO extends BaseModel {
    private Integer id; //关于我们
    private String imageUrl; //图片路径
    private String content; //内容

    @Override
    public String toString() {
        return "AboutUsBO{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
