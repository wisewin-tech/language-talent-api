package com.wisewin.api.service;


import com.wisewin.api.dao.AlipayDAO;
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
     * out_trade_no    支付时传入的商户订单号
     * trade_status	 交易当前状态
     * timestamp    发送请求的时间
     * String out_trade_no, String trade_status, Date timestamp
     * @return
     */
    public boolean getaddAlipay(String out_trade_no, String trade_status){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("out_trade_no",out_trade_no);
        map.put("trade_status",trade_status);


        return  alipayDAO.addAlipay(map);
    }
}
