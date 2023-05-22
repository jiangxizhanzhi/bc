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
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.NodePositionReq;
import com.cyberpeace.situation.controller.req.SituationTopoReq;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.controller.res.EditUrlRes;
import com.cyberpeace.situation.dao.entity.TopoConfig;
import com.cyberpeace.situation.dao.mapper.TopoConfigMapper;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationTopoService;
import com.cyberpeace.situation.service.context.ConfigContext;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 嵌入服务-拓扑
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "嵌入服务-拓扑")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/topo")
public class EmbeddedTopoController {

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoConfigMapper topoConfigMapper;

	@Autowired
	private SituationTopoService situationTopoService;

	/**
	 * 通过模板ID获取大屏ID
	 *
	 * @param templateId 模板id
	 * @return {@code BaseRsp<DashboardIdRes>}
	 */
	@ApiOperation("拓扑编排-通过模板ID获取大屏ID")
	@GetMapping("/template/dashboard/")
	public BaseRsp<DashboardIdRes> getTopoByTemplateId(@RequestParam(value = "template_id",
			required = false) @ApiParam(name = "template_id", value = "模板ID") String templateId) {

		// 获取态势模板信息
		DashboardTemplateContext dashboardTemplateContext = dashboardTemplateRefService
				.getDashboardTemplateContext(templateId);

		if (dashboardTemplateContext == null) {
			throw new BusinessException(SituationErrorCode.UNRELATED_TO_TOPO);
		}

		// 提取大屏ID
		DashboardIdRes dashboardIdRes = new DashboardIdRes();
		dashboardIdRes.setDashboardId(dashboardTemplateContext.getDashboardId());
		return new BaseRsp<>(dashboardIdRes);
	}

	/**
	 * 更新节点坐标
	 *
	 * @param req 节点坐标请求
	 * @return {@code BaseRsp<Boolean>}
	 */
	@ApiOperation("拓扑编排-更新节点坐标")
	@PostMapping("/template/topo/position/")
	public BaseRsp<Boolean> updateNodePosition(@RequestBody @Validated NodePositionReq req) {

		// 获取当前环境ID的拓扑配置
		TopoConfig topoConfig = topoConfigMapper.selectByEnvId(req.getEnvId());
		JSONObject json = JsonUtil.toJsonObject(topoConfig.getConfig(), JSONObject.class);

		// 构造节点坐标map
		Map<String, ElementSlimContext> elementSlimContextMap = JSONObject.parseObject(
				json.toJSONString(), new TypeReference<Map<String, ElementSlimContext>>() {});

		// 更新节点坐标
		req.getPosition().forEach((id, value) -> {
			elementSlimContextMap.get(id).setPosition(value);
		});
		topoConfig.setConfig(JsonUtil.toJsonString(elementSlimContextMap));
		topoConfigMapper.updateById(topoConfig);

		return new BaseRsp<>(true);
	}

	/**
	 * 获取可视化编辑页面路径
	 *
	 * @param modelId 模型id
	 * @return {@code BaseRsp<EditUrlRes>}
	 */
	@ApiOperation("拓扑编排-获取可视化编辑页面路径")
	@GetMapping("/visualization/edit/url/")
	public BaseRsp<EditUrlRes> getEditUrl(@RequestParam(value = "model_id",
			required = false) @ApiParam(name = "model_id", value = "模型ID") String modelId) {
		EditUrlRes editUrlRes = new EditUrlRes(modelId);
		return new BaseRsp<>(editUrlRes);
	}

	/**
	 * 查询拓扑配置
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ConfigContext>}
	 */
	@ApiOperation("配置-单个查询")
	@GetMapping("/config/get/")
	public BaseRsp<ConfigContext>
			getSituationTopoConfig(@RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(situationTopoService.getSituationTopoConfig(resourceId));
	}

	/**
	 * 新增拓扑配置
	 *
	 * @param situationTopoReq 拓扑配置
	 * @return {@code BaseRsp<ConfigContext>}
	 */
	@ApiOperation("配置-新增")
	@PostMapping("/config/create/")
	public BaseRsp<ConfigContext> createSituationTopoConfig(
			@RequestBody @Validated(Create.class) SituationTopoReq situationTopoReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationTopoReq.getName(), null);

		return new BaseRsp<>(situationTopoService.createSituationTopoConfig(situationTopoReq));
	}

	/**
	 * 修改拓扑配置
	 *
	 * @param situationTopoReq 拓扑配置
	 * @return {@code BaseRsp<ConfigContext>}
	 */
	@ApiOperation("配置-修改")
	@PatchMapping("/config/update/")
	public BaseRsp<ConfigContext> updateSituationTopoConfig(
			@RequestBody @Validated(Update.class) SituationTopoReq situationTopoReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationTopoReq.getName(), null);

		return new BaseRsp<>(situationTopoService.updateSituationTopoConfig(situationTopoReq));
	}

	/**
	 * 删除拓扑配置
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("配置-删除")
	@PatchMapping("/config/delete/")
	public BaseRsp<Integer>
			deleteSituationTopoConfig(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		return new BaseRsp<>(
				situationTopoService.deleteSituationTopoConfig(batchDeleteReq.getIds()));
	}

	/**
	 * 查询拓扑配置列表（全量）
	 *
	 * @return {@code BaseRsp<List<ConfigContext>>}
	 */
	@ApiOperation("配置-全量查询")
	@GetMapping("/config/list/")
	public BaseRsp<List<ConfigContext>> getAllConfig() {
		return new BaseRsp<>(situationTopoService.getAllConfig());
	}

}