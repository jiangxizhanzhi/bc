package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.TrainManageService;
import com.cyberpeace.oj.course.service.bo.TrainManageTreeBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "oj-前台课程培训相关api")
@RequiresAuthentication
@RestController
@RequestMapping("/oj/lesson_train")
public class WebLessonTrainController {

    @Autowired
    private TrainManageService trainManageService;

    @ApiOperation("前台培训框架列表查询")
    @GetMapping("/list")
    @RequiresPermissions(MenuConstant.WEB_OJ_TEACH_COURSE)
    public BaseRsp<List<TrainManageTreeBo>> webTrainMangeList() {
        List<TrainManageTreeBo> trainPlanBos = trainManageService.webTrainMangeList();
        return new BaseRsp(trainPlanBos);
    }


}
