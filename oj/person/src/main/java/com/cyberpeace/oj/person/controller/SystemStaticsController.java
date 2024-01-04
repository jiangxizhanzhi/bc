package com.cyberpeace.oj.person.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.auth.controller.req.UserReq;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.auth.service.bo.UserRes;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.person.controller.req.OpLogReq;
import com.cyberpeace.oj.person.controller.res.SystemRecordRes;
import com.cyberpeace.oj.person.controller.res.SystemStaticsRes;
import com.cyberpeace.oj.person.dao.entity.OpLog;
import com.cyberpeace.oj.person.dao.entity.PersonComment;
import com.cyberpeace.oj.person.service.OpLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "oj-系统总览")
@RestController
@RequestMapping("/oj/system/statics")
public class SystemStaticsController {

    @Autowired
    private OpLogService opLogService;
    @Autowired
    private SysUserService sysUserService;
    @ApiOperation("数据统计")
    @GetMapping("/count_statics")
    public BaseRsp<SystemStaticsRes> countStatics(@ApiParam @Validated PageReqBase pageReq) {

        return new BaseRsp<>(new SystemStaticsRes());
    }

    @GetMapping("/record/page")
    public BaseRsp<ListRsp<SystemRecordRes>> page(UserReq req) {
        ListRsp<UserRes> userResListRsp = sysUserService.pageUser(req);
        List<SystemRecordRes> list =new ArrayList<>();
        for (UserRes userRes : userResListRsp.getList()) {
            SystemRecordRes systemRecordRes = new SystemRecordRes();
            systemRecordRes.setUsername(userRes.getUsername());
            systemRecordRes.setName(userRes.getName());
            list.add(systemRecordRes);
        }
        return new BaseRsp<>(new ListRsp<>(list, userResListRsp.getTotal(), userResListRsp.getLastPage(), userResListRsp.getCurrentPage()));
    }


}
