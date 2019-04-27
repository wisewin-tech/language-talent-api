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
    public List<ChapterBO> chapterTist(ChapterBO levelId){
        return chapterDAO.chapterTist(levelId);
    }

    /**
     * 获取某个级别的课时数
     * @param levelId 级别id
     * @return
     */
    public Integer chapterNum(ChapterBO levelId){
        return chapterDAO.chapterNum(levelId);
    }

    /**
     * 课时详情
     * @param id
     * @return
     */
    public ChapterBO chapterDetails(ChapterBO id){
        return chapterDAO.chapterDetails(id);
    }
}
