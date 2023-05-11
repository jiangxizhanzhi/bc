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
import com.cyberpeace.integratation.model.situation.PlatformLinkContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.gis.MapSource;
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

/**
 * 嵌入式gis控制器
 *
 * @author chenhy
 * @date 2023/05/08
 */
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

	/**
	 * 通过模板ID获取平台列表
	 *
	 * @param templateId 模板id
	 * @param type       类型
	 * @return {@code BaseRsp<TemplatePlatformRes>}
	 */
	@ApiOperation("视角编排-通过模板ID获取平台列表")
	@GetMapping("/template/platform/")
	public BaseRsp<TemplatePlatformRes> getPlatformByTemplateId(
			@RequestParam("template_id") @ApiParam(required = true, name = "template_id",
					value = "模板ID") String templateId,
			@RequestParam("type") @ApiParam(required = true, name = "type",
					value = "类型 1-模板 2-实例") Integer type) {

		// 如果是模板，直接返回模板的视角
		if (type.equals(1)) {
			DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(templateId);
			if (dashboardTemplate != null) {
				return new BaseRsp<>(new TemplatePlatformRes(
						JsonUtil.toJsonObject(dashboardTemplate.getViewAngle(), ViewAngle.class)));
			}
		}

		// 如果是实例，返回实例的视角
		DashboardTemplateContext dashboardTemplateContext = dashboardTemplateRefService
				.getDashboardTemplateContext(templateId);

		if (dashboardTemplateContext == null) {
			return new BaseRsp<>(new TemplatePlatformRes());
		}

		// 获取平台列表
		List<PlatformContext> platformContexts = remoteSituationService.getPlatformList(
				dashboardTemplateContext.getTaskId(), dashboardTemplateContext.getSubjectId(),
				dashboardTemplateContext.getSubRangeId(), dashboardTemplateContext.getSchemeId());

		// 获取平台连线列表
		List<PlatformLinkContext> platformLinkContexts = remoteSituationService.getPlatformLinkList(
				dashboardTemplateContext.getTaskId(), dashboardTemplateContext.getSubjectId(),
				dashboardTemplateContext.getSubRangeId(), dashboardTemplateContext.getSchemeId());

		// 构造返回数据
		TemplatePlatformRes templatePlatformRes = new TemplatePlatformRes();
		templatePlatformRes.setPlatformList(platformContexts);
		templatePlatformRes.setPlatformLinkList(platformLinkContexts);
		templatePlatformRes.setViewAngle(
				JsonUtil.toJsonObject(dashboardTemplateContext.getViewAngle(), ViewAngle.class));
		return new BaseRsp<>(templatePlatformRes);
	}

	/**
	 * GIS配置-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ConfigContext>}
	 */
	@ApiOperation("配置-单个查询")
	@GetMapping("/config/get/")
	public BaseRsp<ConfigContext>
			getSituationGisConfig(@RequestParam("config_id") String resourceId) {
		return new BaseRsp<>(situationGisService.getSituationGisConfig(resourceId));
	}

	/**
	 * GIS配置-新增
	 *
	 * @param situationGisReq GIS配置请求
	 * @return {@code BaseRsp<ConfigContext>}
	 */
	@ApiOperation("配置-新增")
	@PostMapping("/config/create/")
	public BaseRsp<ConfigContext> createSituationGisConfig(
			@RequestBody @Validated(Create.class) SituationGisReq situationGisReq) {
		return new BaseRsp<>(situationGisService.createSituationGisConfig(situationGisReq));
	}

	/**
	 * GIS配置-修改
	 *
	 * @param situationGisReq GIS配置请求
	 * @return {@code BaseRsp<ConfigContext>}
	 */
	@ApiOperation("配置-修改")
	@PatchMapping("/config/update/")
	public BaseRsp<ConfigContext> updateSituationGisConfig(
			@RequestBody @Validated(Update.class) SituationGisReq situationGisReq) {
		return new BaseRsp<>(situationGisService.updateSituationGisConfig(situationGisReq));
	}

	/**
	 * GIS配置-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("配置-删除")
	@PatchMapping("/config/delete/")
	public BaseRsp<Integer>
			deleteSituationGisConfig(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		return new BaseRsp<>(situationGisService.deleteSituationGisConfig(batchDeleteReq.getIds()));
	}

	/**
	 * GIS配置-全量查询
	 *
	 * @return {@code BaseRsp<List<ConfigContext>>}
	 */
	@ApiOperation("配置-全量查询")
	@GetMapping("/config/list/")
	public BaseRsp<List<ConfigContext>> getAllConfig() {
		return new BaseRsp<>(situationGisService.getAllConfig());
	}

	/**
	 * 获取地图源选项
	 *
	 * @return {@code BaseRsp<List<StringType>>}
	 */
	@ApiOperation("配置-获取地图源选项")
	@GetMapping("/map/source/list/")
	public BaseRsp<List<StringType>> getMapSourceList(
			@RequestParam(value = "type", required = false) @ApiParam(required = false,
					name = "type", value = "类型 1-影像 2-矢量") Integer type) {
		return new BaseRsp<>(MapSource.getList(type));
	}

	/**
	 * 获取经纬度所属省份
	 *
	 * @param longitude 经度
	 * @param latitude  纬度
	 * @return {@code BaseRsp<String>}
	 */
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

}