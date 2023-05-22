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
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.context.DashboardTemplateRefContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 态势大屏-实例模板
 *
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

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 新增态势模板副本
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DashboardTemplateRefContext>}
	 */
	@ApiOperation("实例模板-新增")
	@PostMapping()
	public BaseRsp<DashboardTemplateRefContext>
			createDashboardTemplate(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefService
				.createDashboardTemplate(resourceId);
		return new BaseRsp<>(
				BeanUtils.copy(dashboardTemplateRef, DashboardTemplateRefContext.class));
	}

	/**
	 * 修改态势模板副本
	 *
	 * @param dashBoardTemplateReq 态势模板请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("实例模板-修改")
	@PatchMapping("")
	public BaseRsp<String> updateDashboardTemplate(
			@RequestBody @Validated(Update.class) DashBoardTemplateReq dashBoardTemplateReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dashBoardTemplateReq.getName(), null);

		return new BaseRsp<>(
				dashboardTemplateRefService.updateDashboardTemplate(dashBoardTemplateReq));
	}

	/**
	 * 修改态势模板副本配置
	 *
	 * @param req 态势模板配置请求
	 * @return {@code BaseRsp<DashboardTemplateRefContext>}
	 */
	@ApiOperation("实例模板-修改配置")
	@PatchMapping("/config")
	public BaseRsp<DashboardTemplateRefContext>
			updateTemplateRefConfig(@RequestBody @Validated DashBoardTemplateConfigReq req) {
		return new BaseRsp<>(dashboardTemplateRefService.updateTemplateRefConfig(req));
	}

	/**
	 * 查询态势模板副本
	 *
	 * @param resourceId 资源id
	 * @param type       视角类型
	 * @return {@code BaseRsp<DashboardTemplateRes>}
	 */
	@ApiOperation("实例模板-单个查询")
	@GetMapping("")
	public BaseRsp<DashboardTemplateRes> getDashboardTemplateInfo(
			@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id",
					value = "资源id") String resourceId,
			@RequestParam(value = "type", required = false) @ApiParam(name = "type",
					value = "视角类型") String type) {
		return new BaseRsp<>(dashboardTemplateRefService.getDashboardTemplateInfo(resourceId, type,
				Boolean.FALSE));
	}

	/**
	 * 删除态势模板副本
	 *
	 * @param dashboardTemplateDelReq 态势模板删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("实例模板-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteDashboardTemplate(
			@RequestBody @Validated DashboardTemplateDelReq dashboardTemplateDelReq) {
		return new BaseRsp<>(dashboardTemplateRefService
				.deleteDashboardTemplate(dashboardTemplateDelReq.getDashboardTemplateIdList()));
	}
}
