package com.cyberpeace.cr.teamtrain.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.teamtrain.service.XdAutomaticDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "xd项目自动造假数据")
@RestController
@RequestMapping("/cr/admin/xd_automatic")
@RequiresAuthentication
@Slf4j
public class XdAutomaticDataController {

    @Autowired
    private XdAutomaticDataService xdAutomaticDataService;

    @GetMapping("/automatic")
    @ApiOperation("自动构造造假数据")
    public BaseRsp<Boolean> automatic() {
        log.info("XdAutomaticDataController.automatic begin.");
        xdAutomaticDataService.automatic();
        return new BaseRsp<>(true);
    }

}
