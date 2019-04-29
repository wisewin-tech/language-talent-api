package com.wisewin.api.service;




import com.wisewin.api.common.constants.UserConstants;
import com.wisewin.api.dao.UserDAO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.param.UserParam;
import com.wisewin.api.util.MD5Util;
import com.wisewin.api.util.RandomUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.util.message.SendMessageUtil;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDAO userDAO;

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
        RedissonHandler.getInstance().set(phone + UserConstants.VERIFY.getValue(), number, 180L);
        //获取缓存中验证码
        String mobileAuthCode = RedissonHandler.getInstance().get(phone + UserConstants.VERIFY.getValue());
        System.out.println("send方法缓存中的验证码为" + mobileAuthCode);
    }


    /**
     * 通过手机号查询用户信息
     */
    public UserBO selectPhone(String phone) {
        System.out.println(phone);
        System.out.println( "UserBO对象:" +userDAO.selectByPhone(phone));
        return userDAO.selectByPhone(phone) ;

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

}