package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.course.service.TrainManageService;
import com.cyberpeace.oj.course.service.bo.TrainCourseBo;
import com.cyberpeace.oj.course.service.bo.TrainManageTreeBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "oj-前台课程培训相关api")
@RequiresAuthentication
@RestController
@RequestMapping("/oj/lesson_train")
public class WebLessonTrainController {

    @Autowired
    private TrainManageService trainManageService;

    @Autowired
    private RemoteLogService remoteLogService;

    @ApiOperation("前台培训框架列表查询")
    @GetMapping("/list")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    public BaseRsp<List<TrainManageTreeBo>> webTrainMangeList() {
        List<TrainManageTreeBo> trainPlanBos = trainManageService.webTrainMangeList();
        return new BaseRsp(trainPlanBos);
    }


}
