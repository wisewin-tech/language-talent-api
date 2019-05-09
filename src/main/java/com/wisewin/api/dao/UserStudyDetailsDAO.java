package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.UserStudyDetailsBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserStudyDetailsDAO {
    /**
     * 获取用户学习详情
     * @param userId
     * @return
     */
    UserStudyDetailsBO getStudyDetails(@Param("userId") Integer userId, @Param("studyDate") Date studyDate);

    /**
     * 添加时长
     * @param userId
     * @return
     */
    void insertDuration(Integer userId);

    /**
     * 修改时长
     * @param userId 用户id
     * @param studyDuration
     */
    void updateDuration(@Param("userId") Integer userId, @Param("studyDuration") Integer studyDuration,
                        @Param("studyDate") Date studyDate);

    /**
     * 最近一周每天学习时长
     * @param userId 用户id
     * @return
     */
    List<UserStudyDetailsBO> weekStudyDuration(Integer userId);
}
