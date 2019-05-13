
package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.OrderBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDAO {
    /**
     * 查询用户订单记录
     * @param map
     * @return
     */
    List<OrderBO> selectAll(Map<String,Object> map);
    OrderBO selectDetails(@Param("id") Integer id, @Param("userId") Integer userId);
    /**
     * 插入订单
     */
    Integer insertOrder(OrderBO order);
}
