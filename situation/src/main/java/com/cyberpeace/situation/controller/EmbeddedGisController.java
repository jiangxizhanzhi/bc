package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.MapSource;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.SituationGisReq;
import com.cyberpeace.situation.controller.res.TemplatePlatformRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.model.StringType;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.SituationGisService;
import com.cyberpeace.situation.service.context.ConfigContext;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "嵌入服务-GIS")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/gis")
public class EmbeddedGisController {

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private DashboardTemplateMapper dashboardTemplateMapper;

	@Autowired
	private SituationGisService situationGisService;

	@ApiOperation("通过模板ID获取平台列表")
	@GetMapping("/template/platform/")
	public BaseRsp<TemplatePlatformRes> getPlatformByTemplateId(
			@RequestParam("template_id") @ApiParam(required = true, name = "template_id",
					value = "模板ID") String templateId,
			@RequestParam("type") @ApiParam(required = true, name = "type",
					value = "类型 1-模板 2-实例") Integer type) {

		if (type.equals(1)) {
			DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(templateId);
			if (dashboardTemplate != null) {
				return new BaseRsp<>(new TemplatePlatformRes(
						JsonUtil.toJsonObject(dashboardTemplate.getViewAngle(), ViewAngle.class)));
			}
		}

		DashboardTemplateContext dashboardTemplateContext = dashboardTemplateRefService
				.getDashboardTemplateContext(templateId);

		if (dashboardTemplateContext == null) {
			return new BaseRsp<>(new TemplatePlatformRes());
		}
		List<PlatformContext> platformContexts = remoteSituationService.getPlatformList(
				dashboardTemplateContext.getTaskId(), dashboardTemplateContext.getSubjectId(),
				dashboardTemplateContext.getSubRangeId(), dashboardTemplateContext.getSchemeId());

		TemplatePlatformRes templatePlatformRes = new TemplatePlatformRes();
		templatePlatformRes.setPlatformList(platformContexts);
		templatePlatformRes.setViewAngle(
				JsonUtil.toJsonObject(dashboardTemplateContext.getViewAngle(), ViewAngle.class));
		return new BaseRsp<>(templatePlatformRes);
	}

	@ApiOperation("获取Gis配置")
	@GetMapping("/config/get/")
	public BaseRsp<ConfigContext>
			getSituationGisConfig(@RequestParam("config_id") String resourceId) {
		return new BaseRsp<>(situationGisService.getSituationGisConfig(resourceId));
	}

	@ApiOperation("新增Gis配置")
	@PostMapping("/config/create/")
	public BaseRsp<ConfigContext> createSituationGisConfig(
			@RequestBody @Validated(Create.class) SituationGisReq situationGisReq) {
		return new BaseRsp<>(situationGisService.createSituationGisConfig(situationGisReq));
	}

	@ApiOperation("修改Gis配置")
	@PatchMapping("/config/update/")
	public BaseRsp<ConfigContext> updateSituationGisConfig(
			@RequestBody @Validated(Update.class) SituationGisReq situationGisReq) {
		return new BaseRsp<>(situationGisService.updateSituationGisConfig(situationGisReq));
	}

	@ApiOperation("删除Gis配置")
	@PatchMapping("/config/delete/")
	public BaseRsp<Integer>
			deleteSituationGisConfig(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		return new BaseRsp<>(situationGisService.deleteSituationGisConfig(batchDeleteReq.getIds()));
	}

	@ApiOperation("获取全量GIS配置列表")
	@GetMapping("/config/list/")
	public BaseRsp<List<ConfigContext>> getAllConfig() {
		return new BaseRsp<>(situationGisService.getAllConfig());
	}

	@ApiOperation("获取经纬度所属省份")
	@GetMapping("/province/")
	public BaseRsp<String> getProvince(
			@RequestParam("longitude") @ApiParam(required = true, name = "longitude",
					value = "经度") String longitude,
			@RequestParam("latitude") @ApiParam(required = true, name = "latitude",
					value = "维度") String latitude) {
		return new BaseRsp<>(
				situationGisService.getProvince(Float.valueOf(longitude), Float.valueOf(latitude)));
	}

	@ApiOperation("获取大屏地图源选项")
	@GetMapping("/map/source/list/")
	public BaseRsp<List<StringType>> getMapSourceList() {
		return new BaseRsp<>(MapSource.getList());
	}

}