package com.wisewin.api.service;

import com.wisewin.api.dao.UserScoreDAO;
import com.wisewin.api.entity.bo.UserScoreRecordBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserScoreService {
    @Resource
    private UserScoreDAO userScoreDAO;

    /**
     * 查询用户所有课时的答题记录
     * @param userId
     * @return
     */
    public List<UserScoreRecordBO> selectScore(Integer userId,Integer chapterId){
        return userScoreDAO.selectUserScore(userId,chapterId);
    }
    /**
     * 添加某一课时的成绩
     * @param map
     * @return
     */
    public void addScore(Map<String,Object> map){
        userScoreDAO.addUserScore(map);
    }

}
