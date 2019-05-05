package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.ChapterBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.CertificateService;
import com.wisewin.api.service.ChapterService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
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

    @RequestMapping("/chapterList")
    /**
     * 获取课时列表
     */
    public void chapterList(ChapterBO levelId, HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping("/chapterDetails")
    public void chapterDetails(ChapterBO id, HttpServletRequest request, HttpServletResponse response) {
        //参数验证
        if (id == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        ChapterBO chapterBO = chapterService.chapterDetails(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(chapterBO));
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("/getChapterCatalogue")
    public void getChapterCatalogue(ChapterBO levelId, HttpServletRequest request, HttpServletResponse response) {
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
