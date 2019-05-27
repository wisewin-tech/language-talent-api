package com.wisewin.api.service;

import com.wisewin.api.dao.UserStudyDetailsDAO;
import com.wisewin.api.entity.bo.UserStudyDetailsBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service("userStudyDetailsService")
@Transactional
public class UserStudyDetailsService {
    @Resource
    private UserStudyDetailsDAO userStudyDetailsDAO;

    /**
     * 获取用户学习详情
     * @param userId
     * @return
     */
    public UserStudyDetailsBO getStudyDetails(Integer userId,String studyDate){
        return userStudyDetailsDAO.getStudyDetails(userId,studyDate);
    }

    /**
     * 添加时长
     * @param userId
     * @return
     */
    public void insertDuration(Integer userId,Date studydate){
         userStudyDetailsDAO.insertDuration(userId,studydate);
    }

     /**
     * 修改时长
     * @param userId
     * @param studyDuration
     */
     public void updateDuration(Integer userId,Integer studyDuration,String studyDate){

        userStudyDetailsDAO.updateDuration(userId,studyDuration,studyDate);
    }

    /**
     * 最近一周每天学习时长
     * @param userId 用户id
     * @return
     */
    public UserStudyDetailsBO weekStudyDuration(Integer userId,Date studyDate){
        return userStudyDetailsDAO.weekStudyDuration(userId,studyDate);
    }
}
