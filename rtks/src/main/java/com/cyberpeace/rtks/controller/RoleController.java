package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.validation.constraints.NotBlank;

import org.apache.commons.collections.SetUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.course.CourseConfigOutput;
import com.cyberpeace.rtks.controller.course.CourseLayoutInput;
import com.cyberpeace.rtks.controller.req.ListReq;
import com.cyberpeace.rtks.controller.role.*;
import com.cyberpeace.rtks.dao.entity.*;
import com.cyberpeace.rtks.service.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

class RoleListReq<T> extends ListReq<T> {

	@ApiModelProperty(value = "所属框架id，可以为空")
	@Getter
	@Setter
	private String frameId;
}

@Data
class Helper {

	private String idStr;

	private String courseStr;

	public Helper(String idStr, String courseStr) {
		this.idStr = idStr;
		this.courseStr = courseStr;
	}

	public Helper() {
		this("[]", "[]");
	}
}

interface Fields {

	String TASK = "task";

	String ABILITY = "ability";

	String KNOWLEDGE = "knowledge";

	String SKILL = "skill";
}

@Api(tags = "TRKS-岗位管理")
@RestController
@RequestMapping("/rtks/role")
public class RoleController extends BaseListController<Role, RoleAddInput, RoleModifyInput, RoleListReq<Role>, RoleDetailOutput, RoleOutput> {

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

	@Autowired
	private SkillSystemService skillSystemService;

	@Autowired
	private CourseService courseService;

	@Override
	RoleService getService() {
		return roleService;
	}

	@ApiOperation("获取课程列表")
	@GetMapping("/courses")
	public BaseRsp<List<CourseConfigOutput>> getSkillCourses(String resource_id,
			@ApiParam(allowableValues = "knowledge, skill") @NotBlank String field) {
		Role role = roleService.getOr404(resource_id);
		List<String> courseIds = getCourseIdsFromDb(role, field);

		return new BaseRsp<>(getCourseConfigOutputListFromCourseIds(courseIds));
	}

	@ApiOperation("编排课程列表")
	@PostMapping("/courses")
	public BaseRsp<List<CourseConfigOutput>>
			setSkillCourses(@RequestBody @Validated CourseLayoutInput courseLayoutInput) {
		Role role = roleService.getOr404(courseLayoutInput.getResourceId());
		String field = courseLayoutInput.getField();

		// 数据库中既有的课程编排
		List<String> oldIds = getCourseIdsFromDb(role, field);

		// 输入的课程编排
		List<String> inputIds = courseLayoutInput.getCourseIds();

		// 课程数量需要保持一致
		if (!SetUtils.isEqualSet(oldIds, inputIds)) {
			throw new InvalidParamException("输入的课程不正确");
		}

		// 校验通过后保存
		Role roleToSave = new Role();
		roleToSave.setResourceId(role.getResourceId());
		setCourse(roleToSave, field, inputIds);
		roleService.updateById(roleToSave);

		return new BaseRsp<>(getCourseConfigOutputListFromCourseIds(inputIds));
	}

	private String getCourseText(Role role, String field) {
		if (Fields.SKILL.equals(field)) {
			return role.getSkillCourses();
		}
		if (Fields.KNOWLEDGE.equals(field)) {
			return role.getKnowledgeCourses();
		}
		throw new InvalidParamException("Invalid field:" + field);
	}

	private void setCourse(Role role, String field, List<String> courseIds) {
		String toSave = JsonUtil.toJsonString(courseIds);
		if (Fields.SKILL.equals(field)) {
			role.setSkillCourses(toSave);
			return;
		}
		if (Fields.KNOWLEDGE.equals(field)) {
			role.setKnowledgeCourses(toSave);
			return;
		}
		throw new InvalidParamException("Invalid field:" + field);
	}

	private List<String> getCourseIdsFromDb(Role role, String field) {
		String courseText = getCourseText(role, field);

		return JsonUtil.toJsonObjectArray(courseText, String.class);
	}

	private List<CourseConfigOutput>
			getCourseConfigOutputListFromCourseIds(List<String> courseIds) {
		List<CourseConfigOutput> courseConfigOutputList = new ArrayList<>();
		for (String courseId : courseIds) {
			CourseConfigOutput courseConfigOutput = new CourseConfigOutput();
			courseConfigOutput.setResourceId(courseId);

			String name = "课程未找到";
			Course course = courseService.getById(courseId);
			if (course != null) {
				name = course.getName();
			}
			courseConfigOutput.setName(name);
			courseConfigOutputList.add(courseConfigOutput);
		}
		return courseConfigOutputList;
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
			levelService.getOr404(levelId);
			List<Level> childLevels = levelService.getChildLevelsByLevelId(levelId);
			if (!CollectionUtils.isEmpty(childLevels)) {
				throw new InvalidParamException("层级" + levelId + "有子层级，不能绑定岗位");
			}
		}

		boolean isModify = originEntity != null;

		// 职责
		String taskIdStr = handlerInput(isModify, input.getTaskIds(), taskService::checkIds);
		role.setTaskIds(taskIdStr);

		// 能力
		String abilityIdStr = handlerInput(isModify, input.getAbilityIds(),
				abilityService::checkIds);
		role.setAbilityIds(abilityIdStr);

		// 知识点
		String originKnowledgeIdsStr = null;
		if (originEntity != null) {
			originKnowledgeIdsStr = originEntity.getKnowledgeIds();
		}

		Helper knowledgeHelper = handlerInputWithCourse(isModify, input.getKnowledgeIds(),
				originKnowledgeIdsStr, knowledgeService::getCourseConfigs);

		if (knowledgeHelper != null) {
			role.setKnowledgeIds(knowledgeHelper.getIdStr());
			role.setKnowledgeCourses(knowledgeHelper.getCourseStr());
		}

		// 技能
		String originSkillIdsStr = null;
		if (originEntity != null) {
			originSkillIdsStr = originEntity.getSkillIds();
		}

		Helper skillHelper = handlerInputWithCourse(isModify, input.getSkillIds(),
				originSkillIdsStr, skillService::getCourseConfigs);

		if (skillHelper != null) {
			role.setSkillIds(skillHelper.getIdStr());
			role.setSkillCourses(skillHelper.getCourseStr());
		}

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

	@Override
	IPage<Role> getPage(RoleListReq<Role> listReq) {
		return roleService.listRoles(listReq.toPageParam(), listReq.getKeyword(),
				listReq.getFrameId());
	}

	private <T extends RoleOutput> T outputFromRole(Role role, T output) {
		output.setResourceId(role.getResourceId());
		output.setName(role.getName());
		output.setDescription(role.getDescription());

		String levelId = role.getLevelId();
		output.setLevelId(levelId);
		output.setLevelName(levelService.getFullPathById(levelId));

		// 技能体系
		String categoryId = role.getCategory();
		output.setCategory(categoryId);
		SkillSystem skillSystem = skillSystemService.getById(categoryId);
		if (skillSystem != null) {
			output.setCategoryName(skillSystem.getContent());
		}

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

	/***
	 * 校验输入中的职责ids，能力ids，并转换为字符串
	 * @param isModify 更新/新建 更新时，如果输入为null 将被跳过
	 * @param inputIds 输入的id列表
	 * @param checkIds 无返回值函数 输入id的列表 确保所有id对应的对象存在 否则报错
	 */
	private String handlerInput(Boolean isModify, List<String> inputIds,
			Consumer<List<String>> checkIds) {

		// 跳过该字段
		if (isModify && inputIds == null) {
			return null;
		}

		// 直接置空
		if (CollectionUtils.isEmpty(inputIds)) {
			return "[]";
		}

		checkIds.accept(inputIds);

		return JsonUtil.toJsonString(inputIds);
	}

	/**
	 * 校验输入中的知识点ids、技能ids，并输出ids字符串，课程ids字符串
	 * @param isModify 更新/新建 更新时，如果输入为null 将被跳过
	 * @param inputIds 输入的id列表
	 * @param originIdStr 更新时，数据库中既有的id列表的json字符串 用于比较是否发生变化
	 * @param getCourseConfigs 函数: id，返回其课程配置数组
	 */
	private Helper handlerInputWithCourse(Boolean isModify, List<String> inputIds,
			String originIdStr, Function<String, List<CourseConfig>> getCourseConfigs) {

		// 更新时跳过该字段
		if (isModify && inputIds == null) {
			return null;
		}

		// 直接置空
		if (CollectionUtils.isEmpty(inputIds)) {
			return new Helper();
		}

		String idsStr = JsonUtil.toJsonString(inputIds);

		// 字段无变化，跳过该字段
		if (originIdStr.equals(idsStr)) {
			return null;
		}

		// 遍历关联记录，验证其是否存在，同时获取关联课程列表
		List<String> courseIds = new ArrayList<>();
		for (String inputId : inputIds) {
			for (CourseConfig courseConfig : getCourseConfigs.apply(inputId)) {
				String courseId = courseConfig.getResourceId();
				if (!courseIds.contains(courseId)) {
					courseIds.add(courseId);
				}
			}
		}
		String coursesStr = JsonUtil.toJsonString(courseIds);
		return new Helper(idsStr, coursesStr);
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
}
