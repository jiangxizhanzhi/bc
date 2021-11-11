package com.cyberpeace.cr.crbase.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.controller.req.MissionModifyReq;
import com.cyberpeace.cr.crbase.controller.req.MissionReq;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;
import com.cyberpeace.cr.crbase.service.MissionCategoryService;
import com.cyberpeace.cr.crbase.service.MissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "任务相关接口")
@RestController
@RequestMapping("/cr/admin/mission")
@RequiresAuthentication
public class MissionController {

	@Autowired
	private MissionService missionService;

	@ApiOperation("新增任务")
	@PostMapping("/missions")
	public BaseRsp<Mission> addMission(@RequestBody @Validated MissionReq missionReq) {
		Mission toAddMission = requestToMission(missionReq);

		missionService.addMission(toAddMission);

		Mission result = missionService.getById(toAddMission.getResourceId());

		return new BaseRsp(result);
	}

	@ApiOperation("前台更新任务")
	@PutMapping("/missions")
	public BaseRsp<Mission>
			updateMissions(@RequestBody @Validated MissionModifyReq missionModifyReq) {
		if (StringUtils.isBlank(missionModifyReq.getResourceId())) {
			throw new RuntimeException();
		}

		Mission toUpdateMission = requestToMission(missionModifyReq);
		missionService.updateById(toUpdateMission);
		// ??
		Mission result = missionService.getById(toUpdateMission.getResourceId());

		return new BaseRsp(result);
	}

	private Mission requestToMission(MissionReq missionReq) {
		Mission mission = BeanUtils.copy(missionReq, Mission.class);

		mission.setCategoryId(missionReq.getCategory());
		mission.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		mission.setSkillPoints(JSON.toJSONString(missionReq.getSkillPoints()));
		mission.setResourceId(IDGeneratorUtil.getID());

		return mission;
	}

	private Mission requestToMission(MissionModifyReq missionModifyReq) {
		Mission mission = missionService.getById(missionModifyReq.getResourceId());
		if (StringUtils.isNotBlank(missionModifyReq.getTitle())) {
			mission.setTitle(missionModifyReq.getTitle());
		}
		if ((missionModifyReq.getScore() != null)) {
			mission.setScore(missionModifyReq.getScore());
		}
		if (missionModifyReq.getSkillPoints() != null) {
			mission.setSkillPoints(JSON.toJSONString(missionModifyReq.getTitle()));
		}

		return mission;
	}

	@Autowired
	private MissionCategoryService missionCategoryService;

	@ApiOperation("获取任务类型")
	@GetMapping("/missions/get_category/")
	public BaseRsp<List<MissionCategory>> list() {

		List<MissionCategory> data = missionCategoryService.list();

		return new BaseRsp(data);
	}
}
