package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.ElementSlimContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.NodePositionReq;
import com.cyberpeace.situation.controller.req.SituationTopoReq;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.controller.res.EditUrlRes;
import com.cyberpeace.situation.dao.entity.TopoConfig;
import com.cyberpeace.situation.dao.mapper.TopoConfigMapper;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.SituationTopoService;
import com.cyberpeace.situation.service.context.ConfigContext;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "嵌入服务-拓扑")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/topo")
public class EmbeddedTopoController {

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private TopoConfigMapper topoConfigMapper;

	@Autowired
	private SituationTopoService situationTopoService;

	@ApiOperation("通过模板ID获取大屏ID")
	@GetMapping("/template/dashboard/")
	public BaseRsp<DashboardIdRes> getTopoByTemplateId(@RequestParam(value = "template_id",
			required = false) @ApiParam(name = "template_id", value = "模板ID") String templateId) {

		DashboardTemplateContext dashboardTemplateContext = dashboardTemplateRefService
				.getDashboardTemplateContext(templateId);

		if (dashboardTemplateContext == null) {
			throw new BusinessException(SituationErrorCode.UNRELATED_TO_TOPO);
		}

		DashboardIdRes dashboardIdRes = new DashboardIdRes();
		dashboardIdRes.setDashboardId(dashboardTemplateContext.getDashboardId());
		return new BaseRsp<>(dashboardIdRes);
	}

	@ApiOperation("更新拓扑节点坐标")
	@PostMapping("/template/topo/position/")
	public BaseRsp<Boolean> updateNodePosition(@RequestBody @Validated NodePositionReq req) {

		TopoConfig topoConfig = topoConfigMapper.selectByEnvId(req.getEnvId());

		JSONObject json = JsonUtil.toJsonObject(topoConfig.getConfig(), JSONObject.class);

		Map<String, ElementSlimContext> elementSlimContextMap = JSONObject.parseObject(
				json.toJSONString(), new TypeReference<Map<String, ElementSlimContext>>() {});

		req.getPosition().forEach((id, value) -> {
			elementSlimContextMap.get(id).setPosition(value);
		});
		topoConfig.setConfig(JsonUtil.toJsonString(elementSlimContextMap));
		topoConfigMapper.updateById(topoConfig);

		return new BaseRsp<>(true);
	}

	@ApiOperation("获取Topo配置")
	@GetMapping("/config/get/")
	public BaseRsp<ConfigContext>
			getSituationTopoConfig(@RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(situationTopoService.getSituationTopoConfig(resourceId));
	}

	@ApiOperation("新增Topo配置")
	@PostMapping("/config/create/")
	public BaseRsp<ConfigContext> createSituationTopoConfig(
			@RequestBody @Validated(Create.class) SituationTopoReq situationTopoReq) {
		return new BaseRsp<>(situationTopoService.createSituationTopoConfig(situationTopoReq));
	}

	@ApiOperation("修改Topo配置")
	@PatchMapping("/config/update/")
	public BaseRsp<ConfigContext> updateSituationTopoConfig(
			@RequestBody @Validated(Update.class) SituationTopoReq situationTopoReq) {
		return new BaseRsp<>(situationTopoService.updateSituationTopoConfig(situationTopoReq));
	}

	@ApiOperation("删除Topo配置")
	@PatchMapping("/config/delete/")
	public BaseRsp<Integer>
			deleteSituationTopoConfig(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		return new BaseRsp<>(
				situationTopoService.deleteSituationTopoConfig(batchDeleteReq.getIds()));
	}

	@ApiOperation("获取全量拓扑配置列表")
	@GetMapping("/config/list/")
	public BaseRsp<List<ConfigContext>> getAllConfig() {
		return new BaseRsp<>(situationTopoService.getAllConfig());
	}

	@ApiOperation("获取跳转可视化编辑路径")
	@GetMapping("/visualization/edit/url/")
	public BaseRsp<EditUrlRes> getEditUrl(@RequestParam(value = "model_id",
			required = false) @ApiParam(name = "model_id", value = "模型ID") String modelId) {
		EditUrlRes editUrlRes = new EditUrlRes(modelId);
		return new BaseRsp<>(editUrlRes);
	}
}