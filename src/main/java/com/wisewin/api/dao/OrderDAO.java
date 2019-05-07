
package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.OrderBO;

import java.util.List;
import java.util.Map;

public interface OrderDAO {
    /**
     * 查询用户订单记录
     * @param map
     * @return
     */
    List<OrderBO> selectAll(Map<String,Object> map);
    OrderBO selectDetails(Integer id,Integer userId);

}
