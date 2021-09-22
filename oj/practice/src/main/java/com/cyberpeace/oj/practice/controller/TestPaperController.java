package com.cyberpeace.oj.practice.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.practice.controller.req.TestPaperReq;
import com.cyberpeace.oj.practice.dao.entity.TestPaper;
import com.cyberpeace.oj.practice.service.TestPaperService;
import com.cyberpeace.oj.practice.service.bo.TestPaperInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "试卷库")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/test-paper")
public class TestPaperController {

    @Autowired
    TestPaperService testPaperService;

    @PostMapping()
    @ApiOperation("创建试卷")
    public BaseRsp<TestPaperInfo> create(@RequestBody TestPaperReq requestData) {
        return new BaseRsp<>(testPaperService.create(requestData));
    }

    @GetMapping()
    @ApiOperation("获取试卷")
    public BaseRsp<TestPaperInfo> get(@RequestParam("resource_id") String resourceId) {
        return new BaseRsp<>(testPaperService.getSingleInfo(resourceId));
    }

    @GetMapping("/list")
    @ApiOperation("获取试卷列表")
    public BaseRsp<ListRsp<TestPaper>> getList(PageReqBase queryParams) {
        return new BaseRsp<>(testPaperService.getList(queryParams.toPageParam(), queryParams.getPattern()));
    }
}
