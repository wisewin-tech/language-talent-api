package com.wisewin.api.service;

import com.wisewin.api.dao.UserStudyDetailsDAO;
import com.wisewin.api.entity.bo.UserStudyDetailsBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public UserStudyDetailsBO getStudyDetails(Integer userId,Date studyDate){
        return userStudyDetailsDAO.getStudyDetails(userId,studyDate);
    }

    /**
     * 添加时长
     * @param userId
     * @return
     */
    public void insertDuration(Integer userId){
         userStudyDetailsDAO.insertDuration(userId);
    }

     /**
     * 修改时长
     * @param userId
     * @param studyDuration
     */
     public void updateDuration(Integer userId,Integer studyDuration,Date studyDate){
        userStudyDetailsDAO.updateDuration(userId,studyDuration,studyDate);
    }

    /**
     * 最近一周每天学习时长
     * @param userId 用户id
     * @return
     */
    public List<UserStudyDetailsBO> weekStudyDuration(Integer userId){
        return userStudyDetailsDAO.weekStudyDuration(userId);
    }
}
