package com.cyberpeace.oj.practice.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.bo.AddOrUpdatePaperContext;
import com.cyberpeace.oj.practice.service.bo.TestPaperInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

@Api(tags = "oj-后台试卷库相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/test-paper")
public class AdminPaperController {

    @Autowired
    private PaperService paperService;

    @PostMapping()
    @ApiOperation("创建试卷")
    public BaseRsp<Paper> create(@RequestBody AddOrUpdatePaperContext requestData) {
        return new BaseRsp<>(paperService.createOrUpdate(requestData));
    }

    @GetMapping()
    @ApiOperation("获取试卷")
    public BaseRsp<TestPaperInfo> get(@RequestParam("resource_id") String resourceId) {
        return new BaseRsp<>(paperService.getSingleInfo(resourceId));
    }

    @GetMapping("/list")
    @ApiOperation("获取试卷列表")
    public BaseRsp<ListRsp<Paper>> getList(PageReqBase queryParams) {
        return new BaseRsp<>(paperService.getList(queryParams.toPageParam(), queryParams.getPattern()));
    }
}
