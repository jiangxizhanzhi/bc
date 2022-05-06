package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-后台训练计划相关api")
@RequiresAuthentication
@RestController
@RequestMapping("/oj/admin/train/plan")
public class AdminTrainPlanController {

	@Autowired
	private TrainPlanService trainPlanService;

	@ApiOperation("训练计划列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp list() {
		List<TrainPlanBo> trainPlanBos = trainPlanService
				.trainPlanList(UserInfoContext.getUserContext());
		return new BaseRsp(trainPlanBos);
	}

}
