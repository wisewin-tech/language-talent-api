package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.SignBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.UserSignBO;

import java.util.List;
import java.util.Map;

public interface SignDAO {
    //查询用户本月签到信息
    List<SignBO> selectMon(Map<String, Object> map);
    //查询用户表用户签到信息
    UserSignBO selectUserSign(Integer userId);
    //查询最新条用户签到信息
    SignBO selectNew(Integer userId);
    //添加用户签到信息
    void insertSign(Integer userId);
    //修改用户签到信息
    void updateUserSign(UserSignBO userBO);

}
