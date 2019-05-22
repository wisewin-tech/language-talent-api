package com.wisewin.api.service;

import com.wisewin.api.dao.CouplebacDAO;
import com.wisewin.api.entity.bo.CouplebackBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("CouplebackService")
@Transactional
public class CouplebackService {

    @Resource
    private CouplebacDAO couplebacDAO;


    /**
     * 添加反馈信息
     *  Integer userid; //用户id
     * String content; //反馈内容
     *  String contactpattern; //用户联系方式
     * String pattern; //用户联系
     *  String pictureurl; //图片url
     */
    public  boolean getaddCpupleback(Integer userid,String content){

        CouplebackBO couplebackBO=new CouplebackBO(userid,content);

        return  couplebacDAO.addCpupleback(couplebackBO)>0;

    }



}
