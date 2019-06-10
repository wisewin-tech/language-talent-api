package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ChapterService;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.service.base.LogService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wy log
 */
@Controller
@RequestMapping("/chapter")
public class ChapterController extends BaseCotroller {
    @Resource
    private ChapterService chapterService;
    @Resource
    private OrderService orderService;

    @Resource
    LogService logService;
    @RequestMapping("/chapterList")
    /**
     * 获取课时列表
     */
    public void chapterList(Integer levelId, HttpServletRequest request, HttpServletResponse response) {
        logService.startController(null,request,levelId);
        if (levelId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            logService.end("/chapter/chapterList",result);
            return;
        }
        logService.call(" ChapterService.chapterList",levelId);
        List<ChapterBO> chapterBOList = chapterService.chapterList(levelId);
        logService.result(" ChapterService.chapterList",chapterBOList);

        logService.call("ChapterService.chapterNum",levelId);
        Integer chapterNum = chapterService.chapterNum(levelId);
        logService.result("ChapterService.chapterNum",chapterNum);
        Map map = new HashMap();
        map.put("chapterBOList", chapterBOList);
        map.put("chapterNum", chapterNum);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        logService.end("/chapter/chapterList",result);
        super.safeJsonPrint(response, result);
    }

    /**
     * 课时详情
     * @param id 课时id
     * @param courseId 课程id
     * @param request
     * @param response
     */
    @RequestMapping("/chapterDetails")
    public void chapterDetails(Integer id, Integer courseId, HttpServletRequest request, HttpServletResponse response) {
        //获取登录用户信息
        UserBO userBO = super.getLoginUser(request);
        logService.startController(userBO,request,id,courseId);
        Integer userId = userBO.getId();
        if (userId==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000021"));
            super.safeJsonPrint(response, result);
            logService.end("/chapter/chapterDetails",result);
            return;
        }
        //参数验证
        if (id == null||courseId==null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            logService.end("/chapter/chapterDetails",result);
            return;
        }
        logService.call("ChapterService.chapterDetails",id);
        ChapterBO chapterBO = chapterService.chapterDetails(id);
        logService.result(chapterBO);

        logService.call("orderService.getStatusByCourseId",userId,courseId);
        Integer count = orderService.getStatusByCourseId(userId,courseId);
        logService.result(count);
        if (chapterBO!=null) {
            if (count > 0) {
                chapterBO.setBuyOrNot("yes");
            } else {
                chapterBO.setBuyOrNot("no");
            }
        }

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(chapterBO));
        logService.end("/chapter/chapterDetails",result);
        super.safeJsonPrint(response, result);
    }
    /**
     * 获取课时目录
     * @param levelId 级别id
     * @return
     */
    @RequestMapping("/getChapterCatalogue")
    public void getChapterCatalogue(Integer levelId, HttpServletRequest request, HttpServletResponse response) {
        logService.startController(null,request,levelId);
        //参数验证
        if (levelId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            logService.end("/chapter/getChapterCatalogue",result);
            return;
        }
        logService.call("chapterService.getChapterCatalogue",levelId);
        List<ChapterBO> chapterBOList = chapterService.getChapterCatalogue(levelId);
        logService.result(chapterBOList);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(chapterBOList));
        super.safeJsonPrint(response, result);
        logService.end("/chapter/getChapterCatalogue",result);
    }

}
