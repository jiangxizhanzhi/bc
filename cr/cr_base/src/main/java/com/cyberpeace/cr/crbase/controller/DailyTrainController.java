package com.cyberpeace.cr.crbase.controller;


import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.DailyTrainListReq;
import com.cyberpeace.cr.crbase.controller.rsp.DailyTrainRsp;
import com.cyberpeace.cr.crbase.service.DailyTrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "后台-练习资源管理-对抗练习")
@RestController
@Slf4j
@RequestMapping("/cr/daily_train")
public class DailyTrainController {

@Autowired
private DailyTrainService dailyTrainService;

    @ApiOperation("列表查询")
    @GetMapping("/list")
    @RequiresPermissions(MenuConstant.CMS_TRAINING_MODE)
    public BaseRsp<ListRsp<DailyTrainRsp>> list(@Validated DailyTrainListReq listReq) {
        return  new BaseRsp<>(dailyTrainService.list(listReq));
    }
}
