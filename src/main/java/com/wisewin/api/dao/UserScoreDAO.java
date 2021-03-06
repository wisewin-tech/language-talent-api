package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.UserScoreRecordBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserScoreDAO {
    /**
     * 查询 用户课时答题成绩
     * @param userId
     * @return
     */
    List<UserScoreRecordBO> selectUserScore(@Param("userId") Integer userId, @Param("chapterId") Integer chapterId);

    /**
     * 添加某一课时的成绩
     * @param map
     */
    void addUserScore(Map<String,Object> map);

    /**
     * 查询课时成绩
     * @param userId
     * @param chapterId
     * @return
     */
    Integer getScore(@Param("userId")Integer userId,@Param("chapterId") Integer chapterId);

    /**
     * 更新课时成绩
     * @param userId
     * @param chapterId
     */
    void updateScore(@Param("userId")Integer userId,@Param("chapterId") Integer chapterId,@Param("score")Integer score);
}
