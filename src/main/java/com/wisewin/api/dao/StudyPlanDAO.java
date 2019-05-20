package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.LevelBO;
import com.wisewin.api.entity.bo.StudyCoursesBO;

import java.util.List;

public interface StudyPlanDAO {
    /**
     * 通过语言id查找所属课程课时信息
     * @param languageId
     * @return
     */
    List<ChapterBO> getStudyPlan(Integer languageId);

    List<LevelBO> getLevelId(Integer languageId);




}
