package com.wisewin.api.service;

import com.wisewin.api.dao.DictionariesDAO;
import com.wisewin.api.entity.bo.DictionariesBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("DictionariesService")
@Transactional
public class DictionariesService {

    @Resource
    private DictionariesDAO dictionariesDAO;


    /**
     * 查询字典value
     */
    public List<DictionariesBO> getqueryDictionaries(String name){
        if(name.equals("occupation")){
            return  dictionariesDAO.queryDictionaries("职业");
        }else if(name.equals("purpose")){
            return  dictionariesDAO.queryDictionaries("学习语言的目的");
        }else if(name.equals("insterst")){
            return  dictionariesDAO.queryDictionaries("兴趣");
        }else{
            return null;
        }


    }
}
