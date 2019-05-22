package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.AlipaymentOrder;

/**
 * Created by 王彬 on 2019/5/22.
 */
public interface AlipaymentOrderDAO {
    AlipaymentOrder selectByOutTradeNo(String outTradeNo);

    void insetAlipaymentOrder(AlipaymentOrder alipaymentOrder);
}
