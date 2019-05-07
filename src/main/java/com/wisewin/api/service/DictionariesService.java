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
     * 根据outer连接字典类型表来查找数据
     *     Integer id; //字典id
     *     String key; //类型名字
     *    String value; //类型
     *    String outer;连接字典类型表valuename
     */
    public List<DictionariesBO> getqueryDictionaries(Integer id, String key,String value,String outer){
        DictionariesBO dictionariesBO=new DictionariesBO(id,key,value,outer);
        return  dictionariesDAO.queryDictionaries(dictionariesBO);
    }
}
