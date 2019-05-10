package com.wisewin.api.service;

import com.wisewin.api.dao.QuestionDAO;
import com.wisewin.api.entity.bo.QuestionBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 王彬 on 2019/5/7.
 */
@Transactional
@Service("questionService")
public class QuestionService {

    @Resource
    private QuestionDAO questionDAO;

   public  List<QuestionBO> queryQuestionList(Map<String,Object> map){
       return questionDAO.queryQuestionList(map);
    }
}
