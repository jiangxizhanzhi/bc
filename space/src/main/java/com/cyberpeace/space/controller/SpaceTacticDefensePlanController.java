package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.service.SpaceTacticDefensePlanService;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.DefensePlanLevelUpdateReq;
import com.cyberpeace.space.service.context.tactic.SpaceTacticDefensePlanBo;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "战法库-防御战法方案")
@RestController
@RequestMapping("/space/tactic/defense_plan")
@RequiresAuthentication
@Slf4j
public class SpaceTacticDefensePlanController extends SpaceControllerBase<SpaceTacticDefensePlanService, SpaceTacticDefensePlan, SpaceTacticDefensePlanBo, SpacePageReqBase> {

	/**
	 * 获取adetail
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<SpaceTacticDefensePlanBo> }
	 */
	@ApiOperation("详情")
	@GetMapping("")
	@Override
	public BaseRsp<SpaceTacticDefensePlanBo>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		SpaceTacticDefensePlan plan = baseService.getById(resource_id);
		if (null == plan) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SpaceTacticDefensePlanBo bo = plan.entity2Bo(Maps.newHashMap());
		bo.setLevel(baseService.getFrameLevels(plan));
		return new BaseRsp<>(bo);
	}

	@ApiOperation("生成新框架")
	@PostMapping("/renew")
	public BaseRsp<SpaceFrameLevel> updatePlan(@RequestBody DefensePlanLevelUpdateReq req) {
		SpaceFrameLevel level = baseService.renewFrame(req);
		return new BaseRsp(level);
	}

}
