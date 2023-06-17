package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.course.controller.req.TrainMangeAddReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeCourseUpdateReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeUpdateReq;
import com.cyberpeace.oj.course.service.TrainManageService;
import com.cyberpeace.oj.course.service.bo.TrainCourseBo;
import com.cyberpeace.oj.course.service.bo.TrainManageTreeBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "oj-后台培训管理相关api")
@RequiresAuthentication
@RestController
@RequestMapping("/oj/admin/trainMange")
public class AdminTrainMangeController {

    @Autowired
    private TrainManageService trainManageService;

    @Autowired
    private RemoteLogService remoteLogService;

    @ApiOperation("培训框架列表查询")
    @GetMapping("/list")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    public BaseRsp<List<TrainManageTreeBo>> trainMangeList() {
        List<TrainManageTreeBo> trainPlanBos = trainManageService.trainMangeList();
        return new BaseRsp(trainPlanBos);
    }

    @ApiOperation("培训框架新增")
    @PostMapping("")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    public BaseRsp<Boolean> addLevel(@Validated(Create.class) TrainMangeAddReq req) {
        Boolean result = trainManageService.add(req);
        if (result) {
            remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TRAIN_MANAGE, OperationType.CREATE, req.getName());
        }
        return new BaseRsp(result);
    }

    @ApiOperation("培训框架修改")
    @PatchMapping("")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    public BaseRsp<Boolean> updateLevel(@Validated(Update.class) TrainMangeUpdateReq req) {
        Boolean result = trainManageService.update(req);
        if (result) {
            remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TRAIN_MANAGE, OperationType.UPDATE, req.getName());
        }
        return new BaseRsp(result);
    }

    @ApiOperation("培训框架复制")
    @PatchMapping("")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    @ApiImplicitParams({@ApiImplicitParam(name = "resource_id", value = "框架id", dataTypeClass = String.class)})
    public BaseRsp<Boolean> copy(@RequestParam(value = "resource_id") String resourceId) {
        Boolean result = trainManageService.copy(resourceId);
        if (result) {
            remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TRAIN_MANAGE, OperationType.CREATE, resourceId);
        }
        return new BaseRsp(result);
    }

    @ApiOperation("培训框架删除")
    @DeleteMapping("")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    @ApiImplicitParams({@ApiImplicitParam(name = "resource_id", value = "框架id", dataTypeClass = String.class)})
    public BaseRsp<Boolean> deleteLevel(@RequestParam(value = "resource_id") String resourceId) {
        Boolean result = trainManageService.delete(resourceId);
        if (result) {
            remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TRAIN_MANAGE, OperationType.DELETE, resourceId);
        }
        return new BaseRsp(result);
    }


    @ApiOperation("培训框架课程查询")
    @GetMapping("/detail")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    @ApiImplicitParams({@ApiImplicitParam(name = "resource_id", value = "框架id", dataTypeClass = String.class)})
    public BaseRsp<List<TrainCourseBo>> getDetail(@RequestParam(value = "resource_id") String resourceId) {
        List<TrainCourseBo> detailList = trainManageService.getDetail(resourceId);
        return new BaseRsp(detailList);
    }

    @ApiOperation("培训课程修改")
    @PatchMapping("")
//    @RequiresPermissions(MenuConstant.CMS_TRAIN_MANAGE)
    public BaseRsp<Boolean> updateCourse(@Validated(Update.class) TrainMangeCourseUpdateReq req) {
        Boolean result = trainManageService.updateCourse(req);
        if (result) {
            remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TRAIN_MANAGE, OperationType.UPDATE, req.getResourceId());
        }
        return new BaseRsp(result);
    }
}
