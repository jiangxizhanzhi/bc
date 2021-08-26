package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.rtks.service.RtksStatisticService;
import com.cyberpeace.oj.rtks.service.bo.RoleStudyBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiresAuthentication
@RestController
@RequestMapping("/rtks/statistic")
@Api("RTKS-岗位相关统计")
public class RtksStatisticController {

    @Autowired
    private RtksStatisticService rtksStatisticService;



}
