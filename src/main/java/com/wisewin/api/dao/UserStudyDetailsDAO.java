package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.UserStudyDetailsBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserStudyDetailsDAO {
    /**
     * 获取用户学习详情
     * @param userId
     * @return
     */
    UserStudyDetailsBO getStudyDetails(@Param("userId") Integer userId, @Param("studyDate") String studyDate);

    /**
     * 添加时长
     * @param userId
     * @return
     */
    void insertDuration(@Param("userId") Integer userId,@Param("studydate") Date studydate);

    /**
     * 修改时长
     * @param userId 用户id
     * @param studyDuration
     */
    void updateDuration(@Param("userId") Integer userId, @Param("studyDuration") Integer studyDuration,
                        @Param("studyDate") String studyDate);

    /**
     * 最近一周每天学习时长
     * @param userId 用户id
     * @return
     */
    UserStudyDetailsBO weekStudyDuration(@Param("userId") Integer userId,@Param("studyDate")Date studyDate);
}
