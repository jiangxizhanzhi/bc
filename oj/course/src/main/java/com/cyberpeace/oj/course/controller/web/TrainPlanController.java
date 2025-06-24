package com.cyberpeace.oj.course.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.TrainPlanWebReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanWebRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.service.TrainPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
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


    /**
     * 我的学习
     * 我的教学课程
     *
     * @param trainPlanReq 训练计划请求体
     * @return {@code BaseRsp<ListRsp<TrainPlanWebRsp>> }
     * @throws
     * @see TrainPlanWebReq
     * @see BaseRsp<ListRsp<TrainPlanWebRsp>>
     */
    @ApiOperation("训练计划列表分页查询")
    @GetMapping("/list/page")
    @RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
            logical = Logical.OR)
    public BaseRsp<ListRsp<TrainPlanWebRsp>> page(@Validated TrainPlanWebReq trainPlanReq) {

        IPage<TrainPlanWebRsp> data = trainPlanService.page(trainPlanReq,
                UserInfoContext.getUserContext());

        ListRsp<TrainPlanWebRsp> listResult = PageUtil.getListResult(data);
        return new BaseRsp<>(listResult);
    }
}
