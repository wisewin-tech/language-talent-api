package com.wisewin.api.service;

import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.KeyValDAO;
import com.wisewin.api.dao.RecordDAO;
import com.wisewin.api.dao.SignDAO;
import com.wisewin.api.entity.bo.RecordBO;
import com.wisewin.api.entity.bo.UserSignBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Resource
    private UserService userService;
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
        //咖豆增加的数量 整数integer
        Integer integer=num/scale;
        //咖豆增加数量的余数 remainder
        Integer remainder=num%scale;
        if (integer==0){  //如果所兑换数量不足于兑换一个咖豆
            String error="积分兑换不足";
            return false;
        }
        if (remainder!=0){  //如果积分兑换整除有余数
            // reduce=用户积分-输入的兑换数量
            //剩余积分=reduce+剩余的余数
            reduce=reduce+remainder;

            user.setIntegral(reduce);
        }else{
            //积分减少  减少的数量为输入的数量
            user.setIntegral(reduce);
        }

        //咖豆增加   integer咖豆增加的数量
        user.setCurrency(user.getCurrency()+integer);
        //修改用户表积分咖豆情况
        recordDAO.updateUser(user);
        //记录表添加积分支出的兑换记录
        RecordBO recordBO=new RecordBO(userId,num-remainder);
        //来源--积分
        recordBO.setSource(UserConstants.INTEGRAL.getValue());
        //状态--支出
        recordBO.setStatus(UserConstants.DECREASE.getValue());
        recordBO.setDescribe("积分兑换咖豆");
        //添加一条数据,userid用户支出积分 num-remainder
        recordDAO.insertUserAction(recordBO);
        RecordBO record=new RecordBO(userId,integer);
        //来源--咖豆
        record.setSource(UserConstants.CURRENCY.getValue());
        //状态--获取
        record.setStatus(UserConstants.INCREASE.getValue());
        record.setDescribe("积分兑换咖豆");
        //添加一条数据,userid用户获取咖豆num
        recordDAO.insertUserAction(record);
        return true;
    }


    /**
     * 兑换积分数量  creditsExchange
     * 兑换咖豆数量  forCoffeeBeans
     * @param num
     * @return
     */
    public Map<String, Object> exchangeInformation(Integer num){
        Map<String,Object> map=new HashMap<String, Object>();
        //获取配置表积分兑换咖豆比例
        String val=keyValDAO.selectKey(UserConstants.SCALE.getValue());
        Integer scale=Integer.parseInt(val);
        //咖豆增加的数量 整数integer
        Integer integer=num/scale;
        //咖豆增加数量的余数 remainder
        Integer remainder=num%scale;

        if (integer==0){
            map.put("creditsExchange",0);
        }else{
            map.put("creditsExchange",num-remainder);
        }
        map.put("exchangeRatio",val);
        map.put("forCoffeeBeans",integer);
        return map;
    }
    /**
     * 兑换比例  val
     * @return
     */
    public Map<String, Object> exchangeInformation(){
        Map<String,Object> map=new HashMap<String, Object>();
        //获取配置表积分兑换咖豆比例
        String val=keyValDAO.selectKey(UserConstants.SCALE.getValue());
        map.put("exchangeRatio",val);
        return map;
    }

    /**
     * 用户充值咖豆，添加充值信息
     */
    public  void getinsertUserAction(Integer userId,String source,String status,Integer specificAmount,String describe,Integer createId){
    RecordBO  recordBO=new RecordBO(userId,source,status,specificAmount,describe,createId);
      recordDAO.insertUserAction(recordBO);
    }


    /**
     * 用户id
     * @param userId
     * @param type  学前热身 |  观看视频 | 课后测试
     */
    public void forPoints(Integer userId, String type) {
        SimpleDateFormat  startSimp=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat  endSimp=new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Map<String,Object>  map=new HashMap<String, Object>();
        map.put("startTime",startSimp.format(new Date()));
        map.put("endTime",endSimp.format(new Date()));
        map.put("userId",userId);

        //完成学前热身
        if(type.equals(UserConstants.WARM.getValue())){
            map.put("describe",UserConstants.COMPLETE_WARM.getValue());
            //查询今天完成次数
            int row = recordDAO.queryNumber(map);
            //查询配置次数
            int number=Integer.parseInt(keyValDAO.selectKey(UserConstants.WARM_UP.getValue()));
            if(row<number){
                userService.addIntegral(userId,Integer.parseInt(keyValDAO.selectKey(UserConstants.WARM_UP_INVITER.getValue())));
                //积分    获取     数量    原因
                this.getinsertUserAction(userId, UserConstants.INTEGRAL.getValue(), UserConstants.INCREASE.getValue(), Integer.parseInt(keyValDAO.selectKey(UserConstants.WARM_UP_INVITER.getValue())), UserConstants.COMPLETE_WARM.getValue(), null);
            }

         //完成教学视频
        }else if(type.equals(UserConstants.WATCH.getValue()))  {
            map.put("describe",UserConstants.COMPLETE_VODE.getValue());
            //查询今天完成次数
            int row = recordDAO.queryNumber(map);
            //查询配置次数
            int number=Integer.parseInt(keyValDAO.selectKey(UserConstants.VIDEO.getValue()));
            if(row<number){
                userService.addIntegral(userId,Integer.parseInt(keyValDAO.selectKey(UserConstants.VIDEO_INVITER.getValue())));
                //积分    获取     数量    原因
                this.getinsertUserAction(userId, UserConstants.INTEGRAL.getValue(), UserConstants.INCREASE.getValue(), Integer.parseInt(keyValDAO.selectKey(UserConstants.VIDEO_INVITER.getValue())), UserConstants.COMPLETE_VODE.getValue(), null);
            }


            //完成随堂测试
        }else if(type.equals(UserConstants.AFTER.getValue()))  {
            map.put("describe",UserConstants.COMPLETE_TEST.getValue());
            //查询今天完成次数
            int row = recordDAO.queryNumber(map);
            //查询配置次数
            int number=Integer.parseInt(keyValDAO.selectKey(UserConstants.TEST.getValue()));
            if(row<number){
                userService.addIntegral(userId,Integer.parseInt(keyValDAO.selectKey(UserConstants.TEST_INVITER.getValue())));
                //积分    获取     数量    原因
                this.getinsertUserAction(userId, UserConstants.INTEGRAL.getValue(), UserConstants.INCREASE.getValue(), Integer.parseInt(keyValDAO.selectKey(UserConstants.TEST_INVITER.getValue())), UserConstants.COMPLETE_TEST.getValue(), null);
            }
        }
    }

}