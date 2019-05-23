package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.param.UserParam;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserDAO {



    /**
     * 添加用户信息
     * @param user
     */
    void insertUser(UserBO user);

    /**
     * 通过phone查询用户信息
     * @param phone
     * @return
     */
    UserBO selectByPhone(String phone);

    /**
     *修改用户信息
     * @param userParam
     * @return
     */
    void updateUser(UserParam userParam);
    /**
     * 通过id查找用户user
     * @param id
     * @return
     */
    UserBO selectAllById(Integer id);


        /* 后面的功能留作备用*/
    /**
     * 手机号修改密码
     * @param phone
     * @param newPassword
     */
    void updatePassword(@Param("phone") String phone,
                        @Param("password") String newPassword);

    /**
     * 通过手机号发送验证码修改密码
     * @param phone
     * @param newpassword
     */
    void changePassword(String phone, String newpassword);

    /**
     *
     * 通过用户名查询用户信息
     * @param name
     * @return
     */
    UserBO  selectUserByUsername(String name);
    //修改正在学习的语言
    void updateLanguage(@Param("id") Integer id,@Param("studyingLanguageId")Integer studyingLanguageId);





    //获取当前用户
    UserBO selectUser(@Param("id") Integer id);

    //扣减用户咖豆
    void updateUserCrrency(@Param("id")String id , @Param("price")Integer price);

    //用户充值咖豆
    Integer updateUserAugment(Map<String,Object> map);

    //修改绑定手机号
    Integer updatePhone(@Param("userId") Integer userId,@Param("phone") String phone);
}
