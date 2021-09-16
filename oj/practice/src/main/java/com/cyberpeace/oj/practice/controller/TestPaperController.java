package com.cyberpeace.oj.practice.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.practice.controller.req.TestPaperReq;
import com.cyberpeace.oj.practice.service.TestPaperService;
import com.cyberpeace.oj.practice.service.bo.TestPaperInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
