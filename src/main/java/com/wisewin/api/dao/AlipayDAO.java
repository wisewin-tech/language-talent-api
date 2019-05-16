package com.wisewin.api.dao;


import com.wisewin.api.entity.AlipayBO;
import com.wisewin.api.entity.bo.OrderBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

/**
 * 支付宝订单号
 */
public interface AlipayDAO {

    /**
     * 添加订单号
     * private Integer userId; //用户id
     * private String orderNumber; //订单号
     * private String orderType; //订单类型(购买/充值
     *  private String creationDate; //购买日期
     * String out_trade_no, String trade_status, Date timestamp
     * @return
     */
    Integer addAlipay(@Param("userId")Integer userId,@Param("orderNumber")String orderNumber,@Param("orderType")String orderType);

}
