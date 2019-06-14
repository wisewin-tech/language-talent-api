package com.wisewin.api.service;

import com.wisewin.api.dao.FeedbackDAO;
import com.wisewin.api.entity.bo.FeedbackBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("CouplebackService")
@Transactional
public class CouplebackService {

    @Resource
    private FeedbackDAO feedbackDAO;


    /**
     * 添加反馈信息
     *  Integer userid; //用户id
     * String content; //反馈内容
     *  String contactpattern; //用户联系方式
     * String pattern; //用户联系
     *  String pictureurl; //图片url
     */
    public  boolean getaddCpupleback(Integer userid,String content,String phone){
        FeedbackBO feedbackBO=new FeedbackBO();
        feedbackBO.setUserId(userid);
        feedbackBO.setContent(content);
        feedbackBO.setContactWay("手机号码");
        feedbackBO.setStatus("unread");
        feedbackBO.setContactNumber(phone);
        return  feedbackDAO.addFeedback(feedbackBO)>0;

    }



}
