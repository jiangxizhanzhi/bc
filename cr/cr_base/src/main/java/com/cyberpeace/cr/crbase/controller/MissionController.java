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
import com.cyberpeace.cr.crbase.controller.req.MissionTemplateModifyReq;
import com.cyberpeace.cr.crbase.controller.req.MissionTemplateReq;
import com.cyberpeace.cr.crbase.controller.res.MissionCategoryRes;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
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
	public BaseRsp<MissionTemplate>
			addMission(@RequestBody @Validated MissionTemplateReq missionTemplateReq) {
		MissionTemplate toAddMissionTemplate = requestToMissionTemplate(missionTemplateReq);

		missionService.addMissionTemplate(toAddMissionTemplate);

		return new BaseRsp();
	}

	@ApiOperation("后台更新任务")
	@PutMapping("/missions")
	public BaseRsp<MissionTemplate>
			updateMissionTemplate(@RequestBody @Validated MissionTemplateModifyReq modifyReq) {
		if (StringUtils.isBlank(modifyReq.getResourceId())) {
			throw new RuntimeException();
		}

		MissionTemplate toUpdateMissionTemplate = requestToMissionTemplate(modifyReq);
		missionService.updateMissionTemplate(toUpdateMissionTemplate);

		return new BaseRsp();
	}

	private MissionTemplate requestToMissionTemplate(MissionTemplateReq missionTemplateReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(missionTemplateReq, MissionTemplate.class);

		missionTemplate.setCategoryId(missionTemplateReq.getCategory());
		missionTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		missionTemplate.setSkillPoints(JSON.toJSONString(missionTemplateReq.getSkillPoints()));
		missionTemplate.setResourceId(IDGeneratorUtil.getID());

		return missionTemplate;
	}

	private MissionTemplate requestToMissionTemplate(MissionTemplateModifyReq modifyReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(modifyReq, MissionTemplate.class);

		missionTemplate.setCategoryId(modifyReq.getCategory());
		missionTemplate.setSkillPoints(JSON.toJSONString(modifyReq.getSkillPoints()));

		return missionTemplate;
	}

	@ApiOperation("前台更新任务")
	@PutMapping("/mission_update")
	public BaseRsp<Mission>
			updateMissions(@RequestBody @Validated MissionModifyReq missionModifyReq) {
		if (StringUtils.isBlank(missionModifyReq.getResourceId())) {
			throw new RuntimeException();
		}

		Mission toUpdateMission = requestToMission(missionModifyReq);
		missionService.updateById(toUpdateMission);

		return new BaseRsp();
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
			mission.setSkillPoints(JSON.toJSONString(missionModifyReq.getSkillPoints()));
		}

		return mission;
	}

	@ApiOperation("获取任务类型")
	@GetMapping("/missions/get_category/")
	public BaseRsp<List<MissionCategory>> list() {

		List<MissionCategory> categories = missionService.listCategories();
		List<MissionCategoryRes> data = BeanUtils.deepCopyList(categories,
				MissionCategoryRes.class);

		return new BaseRsp(data);
	}
}
