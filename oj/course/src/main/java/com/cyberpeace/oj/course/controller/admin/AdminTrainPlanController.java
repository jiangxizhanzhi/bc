package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.controller.req.TrainPlanAddReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanUpdateReq;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
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

	@ApiOperation("训练计划列表查询全部")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<TrainPlanBo>> list() {
		List<TrainPlanBo> trainPlanBos = trainPlanService
				.trainPlanList(UserInfoContext.getUserContext());
		return new BaseRsp(trainPlanBos);
	}

	@ApiOperation("训练计划列表分页查询")
	@GetMapping("/list/page")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<TrainPlanRsp>> page(@Validated PageReqBase pageReqBase) {

		// 默认时间排序
		pageReqBase.setSort("-update_time");

		IPage<TrainPlanRsp> data = trainPlanService.page(pageReqBase,
				UserInfoContext.getUserContext());

		ListRsp<TrainPlanRsp> listResult = PageUtil.getListResult(data);
		for (TrainPlanRsp trainPlanRsp : listResult.getList()) {
			if(StringUtils.isEmpty(trainPlanRsp.getName())){
				trainPlanRsp.setName("临时任务计划");
			}
		}

		return new BaseRsp(listResult);
	}

	@ApiOperation("新增单条训练计划")
	@PostMapping("/train_plan")
	public BaseRsp<String> addTrainPlan(@RequestBody @Validated TrainPlanAddReq trainPlanAddReq) {
		TrainPlan trainPlan = BeanUtils.copy(trainPlanAddReq, TrainPlan.class);

		// 校验name是否已存在
		trainPlanService.checkNameRepeat(trainPlanAddReq.getName(), null);

		trainPlan.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		trainPlanService.save(trainPlan);

		return new BaseRsp<>();
	}

	@ApiOperation("编辑单条训练计划")
	@PutMapping("/train_plan")
	public BaseRsp<String> addAssessModel(@RequestBody @Validated TrainPlanUpdateReq trainPlanUpdateReq) {
		TrainPlan trainPlan = BeanUtils.copy(trainPlanUpdateReq, TrainPlan.class);

		// 校验name是否已存在
		trainPlanService.checkNameRepeat(trainPlanUpdateReq.getName(), trainPlanUpdateReq.getResourceId());

		trainPlan.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		trainPlanService.updateById(trainPlan);

		return new BaseRsp<>();
	}
	@ApiOperation("查询单条训练计划")
	@GetMapping("/train_plan")
	public BaseRsp<TrainPlan> getAssessModel(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		TrainPlan assessModel = trainPlanService.getById(resource_id);

		return new BaseRsp(assessModel);
	}
}
