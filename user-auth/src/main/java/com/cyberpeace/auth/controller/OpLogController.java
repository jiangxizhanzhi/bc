package com.cyberpeace.auth.controller;

import com.cyberpeace.auth.controller.req.OpLogAddReq;
import com.cyberpeace.auth.service.OpLogService;
import com.cyberpeace.auth.service.bo.OpLog;
import com.cyberpeace.auth.service.bo.ResetPwdSelfReq;
import com.cyberpeace.common.model.BaseRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "审计日志")
@RestController
@RequestMapping("/opLog")
public class OpLogController {

    @Autowired
    private OpLogService opLogService;
    @ApiOperation("保存日志")
    @PostMapping("")
    public BaseRsp svaeLog(@RequestBody OpLogAddReq log) {
        opLogService.save(log.getModule(),log.getMethod(),log.getTarget(),log.getDetail());
        return new BaseRsp<>();
    }
}
