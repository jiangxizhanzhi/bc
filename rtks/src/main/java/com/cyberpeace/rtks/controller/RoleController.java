package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.validation.constraints.NotBlank;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
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
import lombok.Getter;
import lombok.Setter;

class RoleListReq<T> extends ListReq<T> {

	@ApiModelProperty(value = "所属框架id，可以为空")
	@Getter
	@Setter
	private String frameId;
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
		Collections.sort(oldIds);
		int oldCount = oldIds.size();

		// 输入的课程编排
		List<String> newIds = courseLayoutInput.getCourseIds();
		int newCount = newIds.size();

		// 课程数量需要保持一致
		if (oldCount != newCount) {
			throw new InvalidParamException("输入课程数量不正确");
		}

		List<String> newIdsSorted = new ArrayList<>(newIds);
		Collections.sort(newIdsSorted);
		// 课程id排序后应当一致
		for (int i = 0; i < oldCount; i++) {
			if (!oldIds.get(i).equals(newIdsSorted.get(i))) {
				throw new InvalidParamException("输入的课程id不正确");
			}
		}

		// 校验通过后保存
		Role roleToSave = new Role();
		roleToSave.setResourceId(role.getResourceId());
		setCourse(roleToSave, field, newIds);
		roleService.updateById(roleToSave);

		return new BaseRsp<>(getCourseConfigOutputListFromCourseIds(newIds));
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

	private void setCourse(Role role, String field, List<String> courseIds) {
		String toSave = JsonUtil.toJsonString(courseIds);
		if ("skill".equals(field)) {
			role.setSkillCourses(toSave);
		} else if ("knowledge".equals(field)) {
			role.setKnowledgeCourses(toSave);
		} else {
			throw new InvalidParamException("");
		}
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
		for (String inputId : inputIds) {
			T t = getById.apply(inputId);
			if (t == null) {
				throw new ResourceNotFoundException(name + inputId + "不存在");
			}
			for (CourseConfig courseConfig : getCourseConfigs.apply(t)) {
				String courseId = courseConfig.getResourceId();
				if (!courseIds.contains(courseId)) {
					courseIds.add(courseId);
				}
			}
		}
		String coursesStr = JsonUtil.toJsonString(courseIds);
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
