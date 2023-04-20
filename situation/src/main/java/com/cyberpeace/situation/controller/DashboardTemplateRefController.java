package com.cyberpeace.situation.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.controller.req.DashBoardTemplateConfigReq;
import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.req.DashboardTemplateDelReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.context.DashboardTemplateRefContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author xuxz
 * @Date 30/11/2022 上午9:54
 */
@Api(tags = "态势大屏-实例模板")
@RestController
@RequiresAuthentication
@RequestMapping("dashboard/template/ref")
public class DashboardTemplateRefController {

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@ApiOperation("新增态势数据")
	@PostMapping()
	public BaseRsp<DashboardTemplateRefContext>
			createDashboardTemplate(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefService
				.createDashboardTemplate(resourceId);
		return new BaseRsp<>(
				BeanUtils.copy(dashboardTemplateRef, DashboardTemplateRefContext.class));
	}

	@ApiOperation("修改态势数据")
	@PatchMapping("")
	public BaseRsp<String> updateDashboardTemplate(
			@RequestBody @Validated(Update.class) DashBoardTemplateReq dashBoardTemplateReq) {
		return new BaseRsp<>(
				dashboardTemplateRefService.updateDashboardTemplate(dashBoardTemplateReq));
	}

	@ApiOperation("修改实例(定制模板)配置数据")
	@PatchMapping("/config")
	public BaseRsp<DashboardTemplateRefContext>
			updateTemplateRefConfig(@RequestBody @Validated DashBoardTemplateConfigReq req) {
		return new BaseRsp<>(dashboardTemplateRefService.updateTemplateRefConfig(req));
	}

	@ApiOperation("获取改态势数据详情")
	@GetMapping("")
	public BaseRsp<DashboardTemplateRes> getDashboardTemplateInfo(
			@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id",
					value = "api资源id") String resourceId,
			@RequestParam(value = "type", required = false) @ApiParam(name = "type",
					value = "视角类型") String type) {
		return new BaseRsp<>(dashboardTemplateRefService.getDashboardTemplateInfo(resourceId, type,
				Boolean.FALSE));
	}

	@ApiOperation("批量删除改态势数据")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteDashboardTemplate(
			@RequestBody @Validated DashboardTemplateDelReq dashboardTemplateDelReq) {
		return new BaseRsp<>(dashboardTemplateRefService
				.deleteDashboardTemplate(dashboardTemplateDelReq.getDashboardTemplateIdList()));
	}
}
