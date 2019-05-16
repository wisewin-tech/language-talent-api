package com.wisewin.api.service;


import com.wisewin.api.dao.AlipayDAO;
import com.wisewin.api.entity.AlipayBO;
import com.wisewin.api.entity.bo.OrderBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.query.PageObjectUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service("AlipayService")
public class AlipayService {

    @Resource
    private AlipayDAO alipayDAO;


    /**
     * 添加订单号
     * private Integer userId; //用户id
     * private String orderNumber; //订单号
     * private String orderType; //订单类型(购买/充值
     *  private String creationDate; //购买日期
     * String out_trade_no, String trade_status, Date timestamp
     * @return
     */

    public boolean getaddAlipay(Integer userid,String orderNumber,String orderType,String status){

        return alipayDAO.addAlipay(userid,orderNumber,orderType,status)>0;
    }


    /**
     * 添加支付宝传过来是否支付成功
     * String  out_trade_no;//支付时传入的商户订单号
     * String trade_status; //交易当前状态
     * Double total_amount;//本次交易支付的订单金额，单位为人民币（元）
     * Double receipt_amount;//商家在交易中实际收到的款项，单位为元
     */
    public boolean addAlipayPayment(Map<String,String>  map){
        return  alipayDAO.addAlipayPayment(map)>0;
    }

    /**
     * 修改订单状态
     * status 状态
     * order_number 订单号
     */
    public boolean getupdateOrder(String status,String order_number){
         Map<String,Object> map=new HashMap<String, Object>();
         map.put("status",status);
         map.put("order_number",order_number);

         return  alipayDAO.updateOrder(map)>0;
    }

    /**
     * 修改用户的咖豆数值
     * currency  咖豆
     * id  用户id
     */
    public  boolean getupdateUserCoffee(String currency,Integer id){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("currency",currency);
        map.put("id",id);
        return alipayDAO.updateUserCoffee(map)>0;
    }

    /**
     * 根据用户id查询咖豆
     */
    public UserBO getqueryUserCoffee(Integer id){
        return  alipayDAO.queryUserCoffee(id);
    }

/**
 * 通过支付宝传过来的订单号，在查找订单表的用户id
 */
    public OrderBO getqueryOrderIndent(String order_number){
        return  alipayDAO.queryOrderIndent(order_number);
    }

}
