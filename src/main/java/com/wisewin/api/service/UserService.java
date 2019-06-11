package com.wisewin.api.service;




import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.UserDAO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.param.UserParam;
import com.wisewin.api.util.*;
import com.wisewin.api.util.date.DateUtil;
import com.wisewin.api.util.message.SendMessageUtil;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserDAO userDAO;


    //判断openid是否绑定过
    public String checkBind(String openid,String status){
        //判断是否绑定过
        return userDAO.checkBind(openid,status);
    }
    //添加绑定
    public boolean bindOpenId(String phone,String status,String openId){
        if(userDAO.bindOpenId(phone, status, openId)>0){
            return true;
        }
        return false;
    }


    /**
     * 发送验证码
     *
     * @param phone
     */
    public void send(String phone) {
        //验证手机号格式
        String number = RandomUtils.getRandomNumber(6);
        //发送验证码
        SendMessageUtil.sendSignInCodeMessage(phone, number);
        // 保存验证码信息到Redis
        RedissonHandler.getInstance().set(phone + UserConstants.VERIFY.getValue(), number, 300L);
        //缓存验证码失效标识
        RedissonHandler.getInstance().set(phone + UserConstants.VERIFY_LOSE.getValue(), number, 60L);
        //次数
        String count = RedissonHandler.getInstance().get(phone + UserConstants.DEGREE.getValue());
        if(count!=null){
            //累加
            Integer coun=Integer.parseInt(count);
            coun=coun+1;
            RedissonHandler.getInstance().set(phone + UserConstants.DEGREE.getValue(), coun.toString(), 86400L);
        }else{
            //添加
            RedissonHandler.getInstance().set(phone + UserConstants.DEGREE.getValue(), "1", 86400L);
        }

        //获取缓存中验证码
        String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY.getValue());
        System.out.println("send方法缓存中的验证码为" + mobileAuthCode);
    }


    /**
     * 通过手机号查询用户信息
     */
    public UserBO selectByPhone(String phone) {
//        System.out.println(phone);
//        System.out.println( "UserBO对象:" +userDAO.selectByPhone(phone));

        return userDAO.selectByPhone(phone);

    }
    /**
     * 通过id查询用户信息
     */
    public UserBO selectById(Integer id) {
        return userDAO.selectAllById(id);

    }


    /**
     * 添加用户信息
     *
     * @param userBO
     */
    public void insertUser(UserBO userBO) {

        userDAO.insertUser(userBO);
    }

    /**
     * 修改用户信息
     * @param userParam
     */
    public void updateUser( UserParam userParam) {

        String password=userParam.getPassword();
        //如果用户有修改密码,对密码进行加密
        if (!StringUtils.isEmpty(password)){
            userParam.setPassword(MD5Util.digest(password));
        }

        userDAO.updateUser(userParam);

    }
    /**
     * 用户学习天数
     */
    public UserBO  userLearning( Integer userId) {
        //通过用户id获取用户信息
        UserBO userBO=userDAO.selectAllById(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        //获取今天学习时间
        String now =sdf.format(new Date());
        //获取昨天学习时间
        String yesterday =sdf.format(TimeUtil.getTimeStart(-1));
        //连续学习天数
        Integer continuousLearning=userBO.getContinuousLearning();
        //累计学习天数
        Integer cumulativeLearning=userBO.getCumulativeLearning();
        //上次学习日期
        String lastDate= DateUtil.getStr(userBO.getStudyDate());
        if (yesterday.equals(lastDate)){
            //如果上次学习时间是昨天,连续学习天数+1
            userBO.setContinuousLearning(continuousLearning+1);
        }else {
            //否则,连续学习天数改为1
            userBO.setContinuousLearning(1);
        }
        //累计学习天数+1
        userBO.setCumulativeLearning(cumulativeLearning+1);
        return userBO;

    }

    /**
     * 修改正在学习的语言id
     * @param studyingLanguageId
     * @return
     */
    public void  userUpdate( Integer id,Integer studyingLanguageId) {
        userDAO.updateLanguage(id,studyingLanguageId);

    }

    //用户充值咖豆
    public boolean updateUserAugment(Integer id,Integer currency){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        map.put("currency",currency);
        return  userDAO.updateUserAugment(map)>0;
    }

    //修改绑定手机号
    public boolean updatePhone(Integer userId,String phone){
        return userDAO.updatePhone(userId,phone)>0;
    }

    /**
     * 修改用户学习天数
     * @param continuousLearning 连续学习天数
     * @param cumulativeLearning 累计学习天数
     * @param userId
     */
    public void updateUserStudyDays(Integer continuousLearning, Integer cumulativeLearning, Integer userId){
        userDAO.updateUserStudyDays(continuousLearning, cumulativeLearning, userId);
    }

    /**
     * 查询用户本周连续签到天数
     * @param userId
     * @return
     */
    public Integer getWeekContinuousSign(Integer userId){
        return userDAO.getWeekContinuousSign(userId);
    }

}