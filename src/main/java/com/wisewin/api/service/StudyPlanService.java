package com.wisewin.api.service;

import com.wisewin.api.dao.StudyPlanDAO;
import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.LevelBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("studyPlanService")
@Transactional
public class StudyPlanService {
    @Resource
    private StudyPlanDAO studyPlanDAO;

    /**
     * 通过语言id查找所属课程课时信息
     * @param languageId
     * @return
     */
    public List<ChapterBO> getStudyPlan(Integer languageId){
        return studyPlanDAO.getStudyPlan(languageId);
    }

    public List<LevelBO> getLevelId(Integer languageId){
        return studyPlanDAO.getLevelId(languageId);
    }

}
