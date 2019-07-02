package com.wisewin.api.entity.param;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.math.BigDecimal;

/**
 * @Author: Bin Wang
 * @date: Created in 11:07 2019/7/2
 */
public class IospayParam extends BaseModel {

    BigDecimal price;
    String transactionId;
    String payload;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "IospayParam{" +
                "price=" + price +
                ", transactionId='" + transactionId + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
