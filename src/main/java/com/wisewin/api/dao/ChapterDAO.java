package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.ChapterBO;

import java.util.List;

public interface ChapterDAO {
    /**
     * 课时列表
     * @param levelId 级别id
     * @return
     */
    List<ChapterBO> chapterTist(ChapterBO levelId);

    /**
     * 获取某个级别的课时数
     * @param levelId 级别id
     * @return
     */
    Integer chapterNum(ChapterBO levelId);

    /**
     * 课时详情
     * @param id
     * @return
     */
    ChapterBO chapterDetails(ChapterBO id);
}
