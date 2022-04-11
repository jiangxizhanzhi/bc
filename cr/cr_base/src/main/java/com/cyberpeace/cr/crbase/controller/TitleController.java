package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrUserTitleReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.service.CrTitleService;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleSkillRadarBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleStandardBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("前台-荣誉称号")
@Slf4j
@RequestMapping("/cr/title")
@RestController
@RequiresAuthentication
public class TitleController {
    @Autowired
    private CrTitleService crTitleService;

    @GetMapping("/level/list")
    @ApiOperation("称号等级获取")
    public BaseRsp<List<CrTitleLevel>> levelList(@RequestParam(value = "title", required = false) String title) {
        return new BaseRsp<>(crTitleService.levelFullList(title));
    }

    @GetMapping("/list")
    @ApiOperation("职业名称全量查询")
    public BaseRsp<List<CrTitle>> titleList(@RequestParam(value = "level_id", required = false) String levelId) {
        return new BaseRsp<>(crTitleService.titleFullList(levelId));
    }

    @GetMapping("/user")
    @ApiOperation("当前用户已获得称号")
    public BaseRsp<ListRsp<CrTitleBo>> userTitle(CrUserTitleReq queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(
                crTitleService.userTitle(queryParams.toPageParam(), queryParams.getLevelId(), queryParams.getTitle())
        ));
    }

    @GetMapping("/standard")
    @ApiOperation("评级标准查询")
    public BaseRsp<ListRsp<CrTitleStandardBo>> standardSearch(CrUserTitleReq queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(
                crTitleService.titleStandard(queryParams.toPageParam(), queryParams.getLevelId(), queryParams.getTitle())
        ));
    }

    @GetMapping("/detail")
    @ApiOperation("称号基础信息")
    public BaseRsp<CrTitle> titleDetail(CrUserTitleReq queryParams) {
        return new BaseRsp<>(crTitleService.titleDetail(queryParams.getLevelId(), queryParams.getTitle()));
    }

    @GetMapping("/skill-radar")
    @ApiOperation("技能树分值")
    public BaseRsp<CrTitleSkillRadarBo> skillRadar() {
        return new BaseRsp<>(crTitleService.userSkillRadar());
    }
}
