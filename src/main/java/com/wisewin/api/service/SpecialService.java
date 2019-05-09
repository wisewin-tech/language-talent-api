package com.wisewin.api.service;

import com.wisewin.api.dao.SpecialDAO;
import com.wisewin.api.entity.bo.SpecialBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("SpecialService")
@Transactional
public class SpecialService {

    @Resource
    SpecialDAO specialDAO;

    /**
     * 所有专题的查询
     * */
    public List<SpecialBO> selectSpecialBO(Integer classId){
        return specialDAO.selectSpecialBO(classId);
    }

    /**
     * 某个用户点的某个专题的查询
     * param 用户id 和 专题id
     * */
    public SpecialBO selectSpecialBOById(Integer userId,Integer id){
        //加访问量
        specialDAO.addSpecialTraffic(id);

        //专题的信息
        SpecialBO specialBO=specialDAO.selectSpecialBOById(id);

        //查看这个用户是否喜欢过这个专题
        specialBO.setLike(specialDAO.checkUserLikeSpecial(userId,id)>0?"yes":"no");

        return specialBO;
    }

    /**
     * 把这个专题点为喜欢或者取消喜欢 类似于点赞 可以取消点赞
     * */
    public boolean updSpecialLikeUser(Integer userId,Integer specialId){
        boolean bool=false;//没修改成功

        //判断用户是否喜欢过
        if(specialDAO.checkUserLikeSpecial(userId,specialId)>0){//false
            //喜欢了的话 取消喜欢
            bool=specialDAO.delSpecialLikeUser(userId,specialId)>0;
            specialDAO.updSpecialLikeCount(-1,specialId);

        }else{
            //添加喜欢
            specialDAO.updSpecialLikeCount(1,specialId);
            bool=specialDAO.addSpecialLikeUser(userId,specialId)>0;
        }

        return bool;
    }




}
