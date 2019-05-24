package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.ChapterService;
import com.wisewin.api.service.OrderService;
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

@Controller
@RequestMapping("/chapter")
public class ChapterController extends BaseCotroller {
    @Resource
    private ChapterService chapterService;
    @Resource
    private OrderService orderService;

    @RequestMapping("/chapterList")
    /**
     * 获取课时列表
     */
    public void chapterList(Integer levelId, HttpServletRequest request, HttpServletResponse response) {
        if (levelId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        List<ChapterBO> chapterBOList = chapterService.chapterList(levelId);
        Integer chapterNum = chapterService.chapterNum(levelId);
        Map map = new HashMap();
        map.put("chapterBOList", chapterBOList);
        map.put("chapterNum", chapterNum);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
    }

    /**
     * 课时详情
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/chapterDetails")
    public void chapterDetails(Integer id, Integer courseId, HttpServletRequest request, HttpServletResponse response) {
        //获取登录用户信息
        UserBO userBO = super.getLoginUser(request);
        Integer userId = userBO.getId();
        //参数验证
        if (id == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        ChapterBO chapterBO = chapterService.chapterDetails(id);
        Integer count = orderService.getStatusByCourseId(userId,courseId);
        if (chapterBO!=null) {
            if (count > 0) {
                chapterBO.setBuyOrNot("yes");
            } else {
                chapterBO.setBuyOrNot("no");
            }
        }

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(chapterBO));
        super.safeJsonPrint(response, result);
    }
    /**
     * 获取课时目录
     * @param levelId 级别id
     * @return
     */
    @RequestMapping("/getChapterCatalogue")
    public void getChapterCatalogue(Integer levelId, HttpServletRequest request, HttpServletResponse response) {
        //参数验证
        if (levelId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        List<ChapterBO> chapterBOList = chapterService.getChapterCatalogue(levelId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(chapterBOList));
        super.safeJsonPrint(response, result);

    }

}
