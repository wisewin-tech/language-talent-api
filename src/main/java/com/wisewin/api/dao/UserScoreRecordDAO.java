package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.UserScoreRecordBO;
import org.apache.ibatis.annotations.Param;


public interface UserScoreRecordDAO {
    /**
     *
     * @param userId
     * @param chapterId
     * @return
     */
    Integer getScore(@Param("userId") Integer userId, @Param("chapterId") Integer chapterId);

    /**
     * 添加用户课时成绩
     * @param userScoreRecordBO
     * @return
     */
    Integer addScore(UserScoreRecordBO userScoreRecordBO);
}
