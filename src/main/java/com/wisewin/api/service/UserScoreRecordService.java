package com.wisewin.api.service;

import com.wisewin.api.dao.UserScoreRecordDAO;
import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.UserScoreRecordBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userScoreRecordService")
@Transactional
public class UserScoreRecordService {
    @Resource
    private UserScoreRecordDAO userScoreRecordDAO;

    public Integer getScore(Integer userId, Integer chapterId){
        return userScoreRecordDAO.getScore(userId,chapterId);
    }


    /**
     * 添加用户课时成绩
     * @param userScoreRecordBO
     * @return
     */
    public Integer addScore(UserScoreRecordBO userScoreRecordBO){
        return userScoreRecordDAO.addScore(userScoreRecordBO);
    }
}
