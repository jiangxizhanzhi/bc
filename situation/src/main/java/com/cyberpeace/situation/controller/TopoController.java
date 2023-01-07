package com.cyberpeace.situation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.model.situation.TopoContext;
import com.cyberpeace.integratation.model.situation.req.TopoReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.controller.req.NodePositionReq;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "拓扑接口")
@RestController
@Slf4j
@RequestMapping("/situation/topo")
public class TopoController {

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@ApiOperation("通过模板ID获取拓扑")
	@GetMapping("/template/topo/")
	public BaseRsp<TopoContext>
			getTopoByTemplateId(@RequestParam("template_id") @ApiParam(required = true,
					name = "template_id", value = "模板ID") String templateId) {

		DashboardTemplateContext dashboardTemplateContext = dashboardTemplateRefService
				.getDashboardTemplateContext(templateId);

		if (dashboardTemplateContext == null) {
			return new BaseRsp<>(new TopoContext());
		}
		TopoContext topoContext = remoteSituationService
				.getTopo(BeanUtils.copy(dashboardTemplateContext, TopoReq.class), true);
		return new BaseRsp<>(topoContext);
	}

	@ApiOperation("更新拓扑节点坐标")
	@PostMapping("/template/topo/position/")
	public BaseRsp<Boolean> updateNodePosition(@RequestBody @Validated NodePositionReq req) {
		return new BaseRsp<>(true);
	}
}