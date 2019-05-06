package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.StudyCoursesBO;

import java.util.List;

public interface ChapterDAO {
    /**
     * 课时列表
     * @param levelId 级别id
     * @return
     */
    List<ChapterBO> chapterList(ChapterBO levelId);

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

    /**
     * 获取课时目录
     * @param levelId 级别id
     * @return
     */
    List<ChapterBO> getChapterCatalogue(ChapterBO levelId);


}
