package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.ChapterBO;
import org.apache.ibatis.annotations.Param;


public interface UserScoreRecordDAO {
    /**
     *
     * @param userId
     * @param chapterId
     * @return
     */
    Integer getScore(@Param("userId") Integer userId, @Param("chapterId") Integer chapterId);
}
