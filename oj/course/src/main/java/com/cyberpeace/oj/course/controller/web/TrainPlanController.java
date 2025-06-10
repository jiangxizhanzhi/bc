package com.cyberpeace.oj.course.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanWebReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.controller.res.TrainPlanWebRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.service.TrainPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "oj-训练计划相关api")
@RequiresAuthentication
@RestController
@RequestMapping("/oj/train/plan")
public class TrainPlanController {

    /**
     * 训练计划Service
     *
     * @see TrainPlanService
     */
    @Autowired
    private TrainPlanService trainPlanService;


    @ApiOperation("训练计划列表分页查询")
    @GetMapping("/list/page")
    @RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
    public BaseRsp<ListRsp<TrainPlanWebRsp>> page(@Validated TrainPlanWebReq trainPlanReq) {

        // 默认时间排序
        trainPlanReq.setSort("-update_time");

        IPage<TrainPlanWebRsp> data = trainPlanService.page(trainPlanReq,
                UserInfoContext.getUserContext());

        ListRsp<TrainPlanWebRsp> listResult = PageUtil.getListResult(data);
        for (TrainPlanWebRsp trainPlanRsp : listResult.getList()) {
            if (StringUtils.isEmpty(trainPlanRsp.getName())) {
                // 默认的计划
                trainPlanRsp.setName(TrainPlan.TASK_SET);
                trainPlanRsp.setResourceId(TrainPlan.TASK_SET_ID);
            }
        }

        return new BaseRsp<>(listResult);
    }
}
