package com.wisewin.api.service;


import com.wisewin.api.dao.AlipayDAO;
import com.wisewin.api.entity.AlipayBO;
import com.wisewin.api.entity.bo.OrderBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    public boolean getaddAlipay(Integer userid,String orderNumber,String orderType){

        return alipayDAO.addAlipay(userid,orderNumber,orderType)>0;
    }


}
