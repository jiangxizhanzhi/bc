package com.cyberpeace.cr.crbase.controller;


import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.DailyTrainListReq;
import com.cyberpeace.cr.crbase.controller.rsp.DailyTrainRsp;
import com.cyberpeace.cr.crbase.service.DailyTrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "仿真与资管中心-日常练习")
@RestController
@Slf4j
@RequestMapping("/cr/daily_train")
public class DailyTrainController {

@Autowired
private DailyTrainService dailyTrainService;

    @ApiOperation("列表查询")
    @GetMapping("/list")
//    @RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
    public BaseRsp<ListRsp<DailyTrainRsp>> list(@Validated DailyTrainListReq listReq) {

        dailyTrainService.list(listReq);
        return  new BaseRsp<>();
    }
}
