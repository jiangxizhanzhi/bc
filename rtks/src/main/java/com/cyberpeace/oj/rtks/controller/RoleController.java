package com.cyberpeace.oj.rtks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.rtks.controller.role.*;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.service.*;

import io.swagger.annotations.Api;

@Api(tags = "TRKS-岗位管理")
@RestController
@RequestMapping("/rtks/role")
public class RoleController extends BaseController<Role, RoleInput, RoleAddInput, RoleModifyInput, RoleDetailOutput, RoleOutput, RoleService> {

	@Autowired
	private TaskService taskService;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private SkillService skillService;

	@Autowired
	private AbilityService abilityService;

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	@Override
	RoleService getService() {
		return roleService;
	}

	@Override
	Role getEntityFromAddInput(RoleAddInput roleAddInput) {
		return getRoleFromInput(roleAddInput, false);
	}

	@Override
	Role getEntityFromModifyInput(RoleModifyInput roleModifyInput, Role originEntity) {
		return getRoleFromInput(roleModifyInput, true);
	}

	private Role getRoleFromInput(RoleInput input, Boolean isModify) {
		Role role = new Role();
		role.setName(input.getName());
		role.setDescription(input.getDescription());
		role.setLevelId(input.getLevelId());
		role.setCategory(input.getCategory());
		if (input instanceof RoleModifyInput) {
			role.setResourceId(((RoleModifyInput) input).getResourceId());
		}

		String levelId = input.getLevelId();
		if (levelId != null) {
			levelService.ensureLevelNoChild(levelId);
		}

		role.setTaskIds(checkIds(input.getTaskIds(), taskService, isModify));

		role.setKnowledgeIds(checkIds(input.getKnowledgeIds(), knowledgeService, isModify));

		role.setSkillIds(checkIds(input.getSkillIds(), skillService, isModify));

		role.setAbilityIds(checkIds(input.getAbilityIds(), abilityService, isModify));

		return role;
	}

	@Override
	RoleDetailOutput getOutputFromEntity(Role role) {
		RoleDetailOutput output = outputFromRole(role, new RoleDetailOutput());

		output.setTaskDataList(getRelated(output.getTaskIds(), taskService));

		output.setKnowledgeDataList(getRelated(output.getKnowledgeIds(), knowledgeService));

		output.setSkillDataList(getRelated(output.getSkillIds(), skillService));

		output.setAbilityDataList(getRelated(output.getAbilityIds(), abilityService));

		return output;
	}

	@Override
	RoleOutput getListOutputFromEntity(Role role) {
		return outputFromRole(role, new RoleOutput());
	}

	private <T extends RoleOutput> T outputFromRole(Role role, T output) {
		output.setResourceId(role.getResourceId());
		output.setName(role.getName());
		output.setDescription(role.getDescription());
		output.setLevelId(role.getLevelId());
		output.setCategory(role.getCategory());

		List<String> taskIds = stringToList(role.getTaskIds());
		output.setTaskIds(taskIds);
		output.setTaskCount(taskIds.size());

		List<String> knowledgeIds = stringToList(role.getKnowledgeIds());
		output.setKnowledgeIds(knowledgeIds);
		output.setKnowledgeCount(knowledgeIds.size());

		List<String> skillIds = stringToList(role.getSkillIds());
		output.setSkillIds(skillIds);
		output.setSkillCount(skillIds.size());

		List<String> abilityIds = stringToList(role.getAbilityIds());
		output.setAbilityIds(abilityIds);
		output.setAbilityCount(abilityIds.size());

		return output;
	}

	/**
	 * json字符串转为列表
	 * */
	private List<String> stringToList(String idString) {
		List<String> ret = JsonUtil.toJsonObjectArray(idString, String.class);
		if (ret == null) {
			ret = new ArrayList<>();
		}
		return ret;
	}

	/**
	 * 检查并处理输入中的职责、知识点等id列表是否合法
	 * */
	private <T> String checkIds(List<String> relatedIds, IService<T> service, Boolean keepNull) {
		if (relatedIds == null) {
			if (keepNull) {
				return null;
			}
			return "[]";
		}
		for (String relatedId : relatedIds) {
			T relatedObject = service.getById(relatedId);
			if (relatedObject == null) {
				throw new ResourceNotFoundException(relatedId + "未找到");
			}
		}
		return JsonUtil.toJsonString(relatedIds);
	}

	/**
	 * 输出时获取职责、知识点等详细信息
	 * */
	private <T> List<T> getRelated(List<String> relatedIds, IService<T> service) {
		if (relatedIds.size() == 0) {
			return new ArrayList<>();
		} else {
			return service.listByIds(relatedIds);
		}

	}

	// todo 相关课程编排 修改技能/知识时课程的联动
}
