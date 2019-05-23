package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.LanguageResultBO;
import com.wisewin.api.entity.bo.LevelBO;
import com.wisewin.api.entity.bo.StudyCoursesBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyPlanDAO {
    /**
     * 通过语言id查找所属课程课时信息
     * @param languageId
     * @return
     */
    LevelBO getStudyPlan(@Param("languageId") Integer languageId,@Param("levelId") Integer levelId);

    /**
     * 当levelId为0时查询默认的级别id
     * @return
     */
    Integer getLevelIdByOne();

    /**
     * 获取首页右侧栏级别列表
     * @param languageId
     * @return
     */
    LanguageResultBO getLevelList(Integer languageId);

    /**
     * 获取某个级别下的课时数
     * @param levelId
     * @return
     */
    Integer getLevelCount(Integer levelId);



}
