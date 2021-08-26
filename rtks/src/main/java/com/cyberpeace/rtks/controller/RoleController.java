package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.course.CourseLayoutInput;
import com.cyberpeace.rtks.controller.role.*;
import com.cyberpeace.rtks.dao.entity.*;
import com.cyberpeace.rtks.service.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

	@ApiOperation("获取课程列表")
	@GetMapping("/courses")
	public BaseRsp<List<CourseConfig>> getSkillCourses(String resource_id,
			@ApiParam(allowableValues = "knowledge, skill") @NotBlank String field) {
		Role role = roleService.getOr404(resource_id);
		return new BaseRsp<>(getCourseConfigs(role, field));
	}

	@ApiOperation("编排课程列表")
	@PostMapping("/courses")
	public BaseRsp<List<CourseConfig>>
			setSkillCourses(@RequestBody @Validated CourseLayoutInput courseLayoutInput) {
		Role role = roleService.getOr404(courseLayoutInput.getResourceId());
		String field = courseLayoutInput.getField();

		// 数据库中既有的课程编排
		List<CourseConfig> originConfigs = getCourseConfigs(role, field);
		List<String> originCourseIds = getCourseIds(originConfigs);
		int originCourseCount = originCourseIds.size();

		// 输入的课程编排
		List<CourseConfig> inputConfigs = courseLayoutInput.getCourseConfigList();
		List<String> inputCourseIds = getCourseIds(inputConfigs);
		int inputCourseCount = inputCourseIds.size();

		// 课程数量需要保持一致
		if (originCourseCount != inputCourseCount) {
			throw new InvalidParamException("输入课程数量不正确");
		}

		// 课程id排序后应当一致
		for (int i = 0; i < originCourseCount; i++) {
			if (!originCourseIds.get(i).equals(inputCourseIds.get(i))) {
				throw new InvalidParamException("输入的课程id不正确");
			}
		}

		// 校验通过后保存
		setCourse(role, field, inputConfigs);
		return new BaseRsp<>(inputConfigs);
	}

	private String getCourseText(Role role, String field) {
		if ("skill".equals(field)) {
			return role.getSkillCourses();
		} else if ("knowledge".equals(field)) {
			return role.getKnowledgeCourses();
		} else {
			throw new InvalidParamException("");
		}
	}

	private void setCourse(Role role, String field, List<CourseConfig> courseConfigs) {
		String toSave = JsonUtil.toJsonString(courseConfigs);
		if ("skill".equals(field)) {
			role.setSkillCourses(toSave);
		} else if ("knowledge".equals(field)) {
			role.setKnowledgeCourses(toSave);
		} else {
			throw new InvalidParamException("");
		}
	}

	private List<String> getCourseIds(List<CourseConfig> courseConfigs) {
		List<String> courseIds = new ArrayList<>();
		for (CourseConfig courseConfig : courseConfigs) {
			courseIds.add(courseConfig.getCourseId());
		}
		Collections.sort(courseIds);
		return courseIds;
	}

	private List<CourseConfig> getCourseConfigs(Role role, String field) {

		String courseText = getCourseText(role, field);

		return JsonUtil.toJsonObjectArray(courseText, CourseConfig.class);
	}

	@Override
	Role getEntityFromAddInput(RoleAddInput roleAddInput) {
		return getRoleFromInput(roleAddInput, null);
	}

	@Override
	Role getEntityFromModifyInput(RoleModifyInput roleModifyInput, Role originEntity) {
		Role role = getRoleFromInput(roleModifyInput, originEntity);
		role.setResourceId(roleModifyInput.getResourceId());
		return role;
	}

	private Role getRoleFromInput(RoleInput input, Role originEntity) {
		Role role = new Role();
		role.setName(input.getName());
		role.setDescription(input.getDescription());
		role.setLevelId(input.getLevelId());
		role.setCategory(input.getCategory());

		String levelId = input.getLevelId();
		if (levelId != null) {
			levelService.ensureLevelNoChild(levelId);
		}

		boolean isModify = originEntity != null;

		handlerInput("职责", isModify, taskService::filterIds, input::getTaskIds, role::setTaskIds);

		handlerInput("能力", isModify, abilityService::filterIds, input::getAbilityIds,
				role::setAbilityIds);

		String originKnowledgeIdsStr = null;
		String originSkillIdsStr = null;
		if (originEntity != null) {
			originKnowledgeIdsStr = originEntity.getKnowledgeIds();
			originSkillIdsStr = originEntity.getSkillIds();
		}

		handlerInputWithCourse("知识点", isModify, knowledgeService::getById, input::getKnowledgeIds,
				originKnowledgeIdsStr, role::setKnowledgeIds, role::setKnowledgeCourses,
				knowledgeService::getCourseConfigs);

		handlerInputWithCourse("技能", isModify, skillService::getById, input::getSkillIds,
				originSkillIdsStr, role::setSkillIds, role::setSkillCourses,
				skillService::getCourseConfigs);

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

		String levelId = role.getLevelId();
		output.setLevelId(levelId);
		output.setLevelName(levelService.getFullPathById(levelId));

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

		List<String> knowledgeCourses = stringToList(role.getKnowledgeCourses());
		output.setKnowledgeCourseCount(knowledgeCourses.size());

		List<String> skillCourses = stringToList(role.getSkillCourses());
		output.setSkillCourseCount(skillCourses.size());

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

	private void handlerInput(String name, Boolean isModify,
			Function<List<String>, List<String>> filterIds, Supplier<List<String>> getInputIds,
			Consumer<String> setIds) {

		List<String> inputIds = getInputIds.get();

		// 跳过该字段
		if (isModify && inputIds == null) {
			return;
		}

		// 直接置空
		if (isEmptyList(inputIds)) {
			setIds.accept("[]");
			return;
		}

		String abilityIdsStr = JsonUtil.toJsonString(inputIds);
		setIds.accept(abilityIdsStr);

		List<String> existingIds = filterIds.apply(inputIds);

		for (String t : inputIds) {
			if (!existingIds.contains(t)) {
				throw new ResourceNotFoundException(name + t + "不合法");
			}
		}
	}

	private <T> void handlerInputWithCourse(String name, Boolean isModify,
			Function<String, T> getById, Supplier<List<String>> getInputIds, String originIdStr,
			Consumer<String> setIds, Consumer<String> setCourses,
			Function<T, List<CourseConfig>> getCourseConfigs) {

		List<String> inputIds = getInputIds.get();

		// 更新时跳过该字段
		if (isModify && inputIds == null) {
			return;
		}

		// 直接置空
		if (isEmptyList(inputIds)) {
			setIds.accept("[]");
			setCourses.accept("[]");
			return;
		}

		String idsStr = JsonUtil.toJsonString(inputIds);
		setIds.accept(idsStr);

		// 字段无变化，跳过该字段
		if (originIdStr.equals(idsStr)) {
			return;
		}

		// 遍历关联记录，验证其是否存在，同时获取关联课程列表
		List<String> courseIds = new ArrayList<>();
		List<CourseConfig> courseConfigs = new ArrayList<>();
		for (String inputId : inputIds) {
			T t = getById.apply(inputId);
			if (t == null) {
				throw new ResourceNotFoundException(name + inputId + "不存在");
			}
			for (CourseConfig courseConfig : getCourseConfigs.apply(t)) {
				String courseId = courseConfig.getCourseId();
				if (!courseIds.contains(courseId)) {
					courseIds.add(courseId);
					courseConfigs.add(courseConfig);
				}
			}
		}
		String coursesStr = JsonUtil.toJsonString(courseConfigs);
		setCourses.accept(coursesStr);
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

	private <T> Boolean isEmptyList(List<T> arrayList) {
		if (arrayList == null) {
			return true;
		}
		return arrayList.isEmpty();
	}

}
