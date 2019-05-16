package com.wisewin.api.dao;


import com.wisewin.api.entity.AlipayBO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
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
    Integer addAlipay(@Param("userId")Integer userId,@Param("orderNumber")String orderNumber,@Param("orderType")String orderType,@Param("status")String status);

    /**
     * 添加支付宝传过来是否支付成功
     * String  outTradeNo;//支付时传入的商户订单号
     * String tradeStatus; //交易当前状态
     * Double total_amount;//本次交易支付的订单金额，单位为人民币（元）
     * Double receiptAmount;//商家在交易中实际收到的款项，单位为元
     * Date timestamp;
     */
    Integer  addAlipayPayment(Map<String,String> map);

    /**
     * 修改订单状态
     */
    Integer updateOrder(Map<String,Object>map);

    /**
     * 修改用户的咖豆数值
     */
    Integer updateUserCoffee(Map<String,Object>map);

    /**
     * 根据用户id来查询咖豆
     */
    UserBO queryUserCoffee(@Param("id") Integer id);

    /**
     * 通过支付宝传过来的订单号，在查找订单表的用户id
     */
    OrderBO queryOrderIndent(@Param("order_number") String order_number);

}
