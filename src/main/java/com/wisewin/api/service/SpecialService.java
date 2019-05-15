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
        List<SpecialBO> specialBOList=specialDAO.selectSpecialBO(classId);
        for (SpecialBO specialBO:specialBOList) {
            String date=specialBO.getReleaseDateStr();
            specialBO.setReleaseDateStr(date.substring(0,date.lastIndexOf(".")));
        }
        return specialBOList;
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
    public Integer updSpecialLikeUser(Integer userId,Integer specialId){
        //判断用户是否喜欢过
        if(specialDAO.checkUserLikeSpecial(userId,specialId)>0){//false
            //喜欢了的话 取消喜欢
            specialDAO.delSpecialLikeUser(userId,specialId);
            specialDAO.updSpecialLikeCount(-1,specialId);
            return 0;

        }else{
            //添加喜欢
            specialDAO.updSpecialLikeCount(1,specialId);
            specialDAO.addSpecialLikeUser(userId,specialId);
            return 1;
        }
    }


    /**
     * 如果一个专题分类下 只有这一个专题就显示其他专题分类下的专题
     * */
    public List<SpecialBO> selectOtherSpecialBO(Integer classId,Integer id){
        List<SpecialBO> SpecialBOList=specialDAO.selectSpecialBO(classId);
        if(SpecialBOList.size()>1){//大于两条 显示当前这个分类
            for (SpecialBO special:SpecialBOList) {//删除当前点开的专题
                if(special.getId()==id){
                    SpecialBOList.remove(special);
                    return SpecialBOList;
                }
            }
            return SpecialBOList;
        }else{//小于两条 专题喜欢人数最多的专题
            return specialDAO.selectOtherSpecialBO();
        }

    }


}
