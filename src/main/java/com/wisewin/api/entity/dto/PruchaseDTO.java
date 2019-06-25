package com.wisewin.api.entity.dto;

/**
 * Created by 王彬 on 2019/5/10.
 */
public class PruchaseDTO {
    //标题
    private String title;
    //当前课程价格
    private Integer coursePrice;
    //用户剩余咖豆
    private Integer userCurrency;
    //用户剩余咖豆是否能购买当前课程
    private boolean state;
    //缩略图
    private String img;
    //描述
    private String msg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getUserCurrency() {
        return userCurrency;
    }

    public void setUserCurrency(Integer userCurrency) {
        this.userCurrency = userCurrency;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
