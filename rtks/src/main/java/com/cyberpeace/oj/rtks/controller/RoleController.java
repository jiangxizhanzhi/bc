package com.cyberpeace.oj.rtks.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.rtks.controller.role.*;
import com.cyberpeace.oj.rtks.dao.entity.*;
import com.cyberpeace.oj.rtks.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

		List<String> taskIds = input.getTaskIds();
		String taskIdsStr = getRelatedIdsStr(taskIds, isModify, taskService);
		role.setTaskIds(taskIdsStr);

		List<String> knowledgeIds = input.getKnowledgeIds();
		String knowledgeIdsStr = getRelatedIdsStr(knowledgeIds, isModify, knowledgeService);
		role.setKnowledgeIds(knowledgeIdsStr);

		List<String> skillIds = input.getSkillIds();
		String skillIdsStr = getRelatedIdsStr(skillIds, isModify, skillService);
		role.setSkillIds(skillIdsStr);

		List<String> abilityIds = input.getAbilityIds();
		String abilityIdsStr = getRelatedIdsStr(abilityIds, isModify, abilityService);
		role.setAbilityIds(abilityIdsStr);

		return role;
	}

	@Override
	public RoleDetailOutput getOutputFromEntity(Role role) {
		RoleDetailOutput output = outputFromRole(role, new RoleDetailOutput());

		List<String> taskIds = output.getTaskIds();
		List<Task> taskList = getRoleRelatedObjects(taskIds, taskService);
		output.setTaskDataList(taskList);

		List<String> knowledgeIds = output.getKnowledgeIds();
		List<Knowledge> knowledgeList = getRoleRelatedObjects(knowledgeIds, knowledgeService);
		output.setKnowledgeDataList(knowledgeList);

		List<String> skillIds = output.getSkillIds();
		List<Skill> skillList = getRoleRelatedObjects(skillIds, skillService);
		output.setSkillDataList(skillList);

		List<String> abilityIds = output.getAbilityIds();
		List<Ability> abilityList = getRoleRelatedObjects(abilityIds, abilityService);
		output.setAbilityDataList(abilityList);

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
	 * 给定两个字符串数组，检查toCheck中的每一个元素是否都是target的成员，如果出现任意否，抛出异常
	 * */
	private <T> void compareArrays(List<T> toCheck, List<T> target) {
		for (T t : toCheck) {
			if (!target.contains(t)) {
				throw new ResourceNotFoundException(t + "不合法");
			}
		}
	}

	private String getRelatedIdsStr(List<String> relatedIds, Boolean isModify,
			RoleRelatedService service) {
		String strToSave;
		if (relatedIds == null) {
			if (isModify) {
				// 不更新该字段
				strToSave = null;
			} else {
				strToSave = "[]";
			}
		} else if (relatedIds.size() == 0) {
			strToSave = "[]";
		} else {
			List<String> existingIds = service.filterIds(relatedIds);
			compareArrays(relatedIds, existingIds);
			strToSave = JsonUtil.toJsonString(relatedIds);
		}
		return strToSave;
	}

	/**
	 * 输出时获取职责、知识点等详细信息
	 * */
	private <T> List<T> getRoleRelatedObjects(List<String> relatedIds, IService<T> service) {
		if (relatedIds.size() == 0) {
			return new ArrayList<>();
		} else {
			return service.listByIds(relatedIds);
		}

	}

	// todo 相关课程编排 修改技能/知识时课程的联动
}
