package com.wisewin.api.service;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.RecordDAO;
import com.wisewin.api.dao.SignDAO;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.UserSignBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    @Resource
    private RecordDAO recordDAO;
    @Resource
    private SignDAO signDAO;


    /**
     * 查询积分情况
     * @param map 用户id
     * @return
     */
    public List<RecordBO> selectIntegralInt(Map<String,Object> map){
        //把积分条件加入map集合中
        map.put("source",UserConstants.INTEGRAL.getValue());
        return recordDAO.selectUserAction(map);
    }

    /**
     * 查询积分记录总条数
     * @param userId
     * @return
     */
    public Integer selectUserRecord(Integer userId){
        //把积分条件加入map集合中
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("userId",userId);

        map.put("source",UserConstants.INTEGRAL.getValue());
        return recordDAO.selectUserRecord(map);
    }

    /**
     * 积分兑换咖豆
     * @param userId  用户id
     * @param num     兑换的数量
     */
    public void exchange(Integer userId,Integer num) {
        RecordBO recordBO=new RecordBO(userId,num);
        //来源--积分
        recordBO.setSource(UserConstants.INTEGRAL.getValue());
        //状态--支出
        recordBO.setStatus(UserConstants.DECREASE.getValue());
        //添加一条数据,userid用户支出积分num
        recordDAO.insertUserAction(recordBO);

        //积分兑换咖豆比例未定,暂定为1:1  修改则在num处修改
        RecordBO record=new RecordBO(userId,num);
        //来源--咖豆
        record.setSource(UserConstants.CURRENCY.getValue());
        //状态--获取
        record.setStatus(UserConstants.INCREASE.getValue());
        //添加一条数据,userid用户获取咖豆num
        recordDAO.insertUserAction(record);

        //修改用户表积分咖豆情况
        UserSignBO user=signDAO.selectUser(userId);

        user.setIntegral(user.getIntegral()-num);
        user.setCurrency(user.getCurrency().intValue()+num);
        recordDAO.updateUser(user);

    }

}
