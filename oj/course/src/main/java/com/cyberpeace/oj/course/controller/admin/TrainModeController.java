package com.cyberpeace.oj.course.controller.admin;


import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainModeDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.TrainModeListReq;
import com.cyberpeace.cr.crbase.controller.rsp.TrainModeRsp;
import com.cyberpeace.oj.course.service.TrainingModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "后台-练习资源管理-对抗练习")
@RestController
@Slf4j
@RequestMapping("/oj/training_mode")
public class TrainModeController {

@Autowired
private TrainingModeService trainingModeService;

    @ApiOperation("列表查询")
    @GetMapping("/list")
    @RequiresPermissions(MenuConstant.CMS_TRAINING_MODE)
    public BaseRsp<ListRsp<TrainModeRsp>> list(@Validated TrainModeListReq listReq) {
        return  new BaseRsp<>(trainingModeService.list(listReq));
    }

    @ApiOperation("删除练习")
    @RequiresPermissions(MenuConstant.CMS_TRAINING_MODE)
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestBody TrainModeDeleteReq req) {
        return new BaseRsp<>(trainingModeService.delete(req.getList()));
    }
}
