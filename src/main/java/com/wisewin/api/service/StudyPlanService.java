package com.wisewin.api.service;

import com.wisewin.api.dao.StudyPlanDAO;
import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.LanguageResultBO;
import com.wisewin.api.entity.bo.LevelBO;
import org.apache.ibatis.annotations.Param;
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
    public LevelBO getStudyPlan(Integer languageId,Integer levelId){
        return studyPlanDAO.getStudyPlan(languageId,levelId);
    }
    /**
     * 当levelId为0时查询默认的级别id
     * @return
     */
    public Integer getLevelIdByOne(){
        return studyPlanDAO.getLevelIdByOne();
    }
    /**
     * 获取首页右侧栏级别列表
     * @param languageId
     * @return
     */
    public LanguageResultBO getLevelList(Integer languageId){
        return studyPlanDAO.getLevelList(languageId);
    }

    /**
     * 获取某个级别下的课时数
     * @param levelId
     * @return
     */
    public Integer getLevelCount(Integer levelId){
        return studyPlanDAO.getLevelCount(levelId);
    }

}
