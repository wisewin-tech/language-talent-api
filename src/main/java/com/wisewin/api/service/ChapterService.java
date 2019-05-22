package com.wisewin.api.service;

import com.wisewin.api.dao.ChapterDAO;
import com.wisewin.api.entity.bo.ChapterBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("chapterService")
@Transactional
public class ChapterService {
    @Resource
    private ChapterDAO chapterDAO;

    /**
     * 课时列表
     * @param levelId 级别id
     * @return
     */
    public List<ChapterBO> chapterList(Integer levelId){
        return chapterDAO.chapterList(levelId);
    }

    /**
     * 获取某个级别的课时数
     * @param levelId 级别id
     * @return
     */
    public Integer chapterNum(Integer levelId){
        return chapterDAO.chapterNum(levelId);
    }

    /**
     * 课时详情
     * @param id 课时id
     * @return
     */
    public ChapterBO chapterDetails(Integer id){
        return chapterDAO.chapterDetails(id);
    }

    /**
     * 获取课时目录
     * @param levelId 级别id
     * @return
     */
    public List<ChapterBO> getChapterCatalogue(Integer levelId){
        return chapterDAO.getChapterCatalogue(levelId);
    }

    /**
     * 获取课程id
     * @Pram chapterId
     */
    public Integer getSourceId(Integer chapterId){
        return chapterDAO.getSourceId(chapterId);
    }

}
