package com.cyberpeace.cs.controller;


import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.service.SystemPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "获取系统变量")
@RestController
@RequestMapping("/system_property")
@RequiresAuthentication
@Slf4j
public class SystemPropertyController {

    @Autowired
    private SystemPropertyService systemPropertyService;

    @ApiOperation("全量获取")
    @GetMapping("/list")
    public BaseRsp<List<SystemProperty>> appRunningInfo() {
        List<SystemProperty> list = systemPropertyService.list();
        return new BaseRsp<>(list);
    }
}
