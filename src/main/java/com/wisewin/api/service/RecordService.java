package com.wisewin.api.service;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.KeyValDAO;
import com.wisewin.api.dao.RecordDAO;
import com.wisewin.api.dao.SignDAO;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.UserSignBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    @Resource
    private RecordDAO recordDAO;
    @Resource
    private SignDAO signDAO;
    @Resource
    private KeyValDAO keyValDAO;


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
    public boolean exchange(Integer userId,Integer num) {
       //查看用户积分是否足够兑换
        UserSignBO user=signDAO.selectUser(userId);
        //积分减少
        int reduce=user.getIntegral()-num;
        if (reduce<0){ //如果积分不足于兑换数量
            return false;
        }
        //获取配置表积分兑换咖豆比例
        String val=keyValDAO.selectKey(UserConstants.SCALE.getValue());
        Integer scale=Integer.parseInt(val);
        //咖豆增加的数量
        BigDecimal bd=new BigDecimal(num/scale);
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        bd=bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        int add=bd.intValue();
        if (add<1){
            return false;
        }
        //积分减少
        user.setIntegral(reduce);
        //咖豆增加   bd咖豆增加的数量
        user.setCurrency(user.getCurrency()+add);
        //修改用户表积分咖豆情况
        recordDAO.updateUser(user);
        //记录表添加积分支出的兑换记录
        RecordBO recordBO=new RecordBO(userId,num);
        //来源--积分
        recordBO.setSource(UserConstants.INTEGRAL.getValue());
        //状态--支出
        recordBO.setStatus(UserConstants.DECREASE.getValue());
        //添加一条数据,userid用户支出积分num
        recordDAO.insertUserAction(recordBO);
        RecordBO record=new RecordBO(userId,bd.intValue());
        //来源--咖豆
        record.setSource(UserConstants.CURRENCY.getValue());
        //状态--获取
        record.setStatus(UserConstants.INCREASE.getValue());
        record.setDescribe("积分兑换咖豆");
        //添加一条数据,userid用户获取咖豆num
        recordDAO.insertUserAction(record);
        return true;
    }

}
