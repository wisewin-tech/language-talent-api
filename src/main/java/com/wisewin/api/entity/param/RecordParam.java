package com.wisewin.api.entity.param;


import com.wisewin.api.util.date.DateUtil;

/**
 * 记录表
 */
public class RecordParam {

    private Integer userId; //用户id
    private String source; //来源(咖豆、积分、礼品卡)
    private String status; //增加、减少
    private Integer specificAmount; //具体数额，增减数量
    private String describe; //描述
    private Integer createId; //创建人id
    private String createTime; //创建时间

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSpecificAmount() {
        return specificAmount;
    }

    public void setSpecificAmount(Integer specificAmount) {
        this.specificAmount = specificAmount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = DateUtil.getStr(createTime);
    }
}
