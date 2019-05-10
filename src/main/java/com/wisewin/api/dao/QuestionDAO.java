package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.QuestionBO;

import java.util.List;
import java.util.Map;

/**
 * Created by 王彬 on 2019/5/7.
 */
public interface QuestionDAO {
    /**
     * 根据id查询习题
     */
    List<QuestionBO> queryQuestionList(Map<String,Object> map);

}
