package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleLevelReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleTrainReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.service.CrTitleService;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleTrainBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("后台-荣誉称号")
@Slf4j
@RequestMapping("/cr/admin/title")
@RestController
@RequiresAuthentication
public class AdminTitleController {

    @Autowired
    private CrTitleService crTitleService;

    @PostMapping("/level")
    @ApiOperation("称号等级新增")
    public BaseRsp<CrTitleLevel> create(@RequestBody @Validated CrTitleLevelReq requestData) {
        requestData.validate();
        return new BaseRsp<>(crTitleService.levelCreate(requestData));
    }

    @PatchMapping("/level")
    @ApiOperation("称号等级更新")
    public BaseRsp<CrTitleLevel> update(@RequestBody @Validated CrTitleLevelReq requestData) {
        requestData.validate();
        return new BaseRsp<>(crTitleService.levelUpdate(requestData));
    }

    @GetMapping("/level")
    @ApiOperation("称号等级单个获取")
    public BaseRsp<CrTitleLevel> getLevel(@RequestParam("resource_id") String resourceId) {
        return new BaseRsp<>(crTitleService.levelGet(resourceId));
    }

    @GetMapping("/level/list")
    @ApiOperation("称号等级分页查询")
    public BaseRsp<ListRsp<CrTitleLevel>> list(ListQuery queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(crTitleService.levelList(queryParams.toPageParam(), queryParams.getPattern())));
    }

    @DeleteMapping("/level/batch-delete")
    @ApiOperation("称号等级批量删除")
    public BaseRsp<Boolean> levelBatchDelete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(crTitleService.levelBatchDelete(requestData));
    }

    @GetMapping("/level/all")
    @ApiOperation("称号等级批量删除")
    public BaseRsp<List<CrTitleLevel>> all() {
        return new BaseRsp<>(crTitleService.levelFullList(null));
    }

    @PostMapping()
    @ApiOperation("荣誉称号新增")
    public BaseRsp<CrTitle> create(@RequestBody CrTitleReq requestData) {
        requestData.validate();
        return new BaseRsp<>(crTitleService.titleCreate(requestData));
    }

    @PatchMapping()
    @ApiOperation("荣誉称号更新")
    public BaseRsp<CrTitle> update(@RequestBody CrTitleReq requestData) {
        requestData.validate();
        return new BaseRsp<>(crTitleService.titleUpdate(requestData));
    }

    @GetMapping()
    @ApiOperation("荣誉称号单个获取")
    public BaseRsp<CrTitle> get(@RequestParam("resource_id") String resourceId) {
        return new BaseRsp<>(crTitleService.titleGet(resourceId));
    }

    @GetMapping("/list")
    @ApiOperation("荣誉称号分页查询")
    public BaseRsp<ListRsp<CrTitleBo>> create(ListQuery queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(crTitleService.titleList(queryParams.toPageParam(), queryParams.getPattern())));
    }

    @DeleteMapping("/batch-delete")
    @ApiOperation("荣誉称号批量删除")
    public BaseRsp<Boolean> titleBatchDelete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(crTitleService.titleBatchDelete(requestData.getIds()));
    }

    @GetMapping("/cr-events/list")
    @ApiOperation("荣誉称号-训练分页查询")
    public BaseRsp<ListRsp<CrTitleTrainBo>> eventsList(CrTitleTrainReq queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(
                crTitleService.eventsList(queryParams.toPageParam(), queryParams.getBusinessType(), queryParams.getPattern())
        ));
    }

    @GetMapping("/cr-events/using/list")
    @ApiOperation("荣誉称号-已关联训练分页查询")
    public BaseRsp<ListRsp<CrTitleTrainBo>> usingEventsList(@RequestParam("resource_id") String resourceId, CrTitleTrainReq queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(
                crTitleService.usingEventsList(resourceId, queryParams.toPageParam(), queryParams.getBusinessType(), queryParams.getPattern())
        ));
    }
}
