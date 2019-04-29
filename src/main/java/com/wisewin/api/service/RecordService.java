package com.wisewin.api.service;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.RecordDAO;
import com.wisewin.api.entity.bo.RecordBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    @Resource
    private RecordDAO recordDAO;

    /**
     * 查询积分情况
     * @param map 用户id
     * @return
     */
    public List<RecordBO> selectIntegralInt(Map<String,Object> map){
        //把积分条件加入map集合中
        map.put("source",UserConstants.Integral.getValue());
        return recordDAO.selectUserAction(map);
    }

}
