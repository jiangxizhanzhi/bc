package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleLevelReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.service.CrTitleService;
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

    @PostMapping("/level/create")
    @ApiOperation("称号等级新增")
    public BaseRsp<CrTitleLevel> create(@RequestBody @Validated CrTitleLevelReq requestData) {
        return new BaseRsp<>(crTitleService.levelCreate(requestData));
    }

    @PatchMapping("/level/update")
    @ApiOperation("称号等级更新")
    public BaseRsp<CrTitleLevel> update(@RequestBody @Validated CrTitleLevelReq requestData) {
        return new BaseRsp<>(crTitleService.levelUpdate(requestData));
    }

    @GetMapping("/level/list")
    @ApiOperation("称号等级分页查询")
    public BaseRsp<ListRsp<CrTitleLevel>> list(ListQuery queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(crTitleService.levelList(queryParams.toPageParam(), queryParams.getName())));
    }

    @DeleteMapping("/level/batch-delete")
    @ApiOperation("称号等级批量删除")
    public BaseRsp<Boolean> batchDelete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(crTitleService.levelBatchDelete(requestData));
    }

    @GetMapping("/level/all")
    @ApiOperation("称号等级批量删除")
    public BaseRsp<List<CrTitleLevel>> all() {
        return new BaseRsp<>(crTitleService.levelFullGet());
    }
}
