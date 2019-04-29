package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.param.UserParam;
import org.apache.ibatis.annotations.Param;

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
     * 手机号修改密码
     * @param phone
     * @param newPassword
     */
    void updatePassword(@Param("phone") String phone,
                        @Param("password") String newPassword);

    /**
     * 通过id查找用户user
     * @param id
     * @return
     */

    UserBO selectById(Integer id);

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



}