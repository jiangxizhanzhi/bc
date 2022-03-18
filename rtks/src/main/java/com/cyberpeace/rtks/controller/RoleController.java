package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import org.apache.commons.collections.SetUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.course.CourseConfigOutput;
import com.cyberpeace.rtks.controller.course.CourseLayoutInput;
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

class RoleListReq<T> extends PageReqBase {

	@ApiModelProperty(value = "所属框架id，可以为空")
	@Getter
	@Setter
	private String frameId;

	@ApiModelProperty(value = "模糊查询关键词")
	@Getter
	@Setter
	private String keyword;
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

	String KNOWLEDGE = "knowledge";

	String SKILL = "skill";
}

@Api(tags = "TRKS-岗位管理")
@RestController
@RequestMapping("/rtks/role")
@RequiresAuthentication
public class RoleController {

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
	private CourseNameService courseNameService;

	private Boolean singleFrame;

	@PostConstruct
	public void init() {
		String singleFrameConfig = ConfigUtil.getContextProperty("singleFrame");
		singleFrame = Boolean.parseBoolean(singleFrameConfig);
	}

	protected RoleService getService() {
		return roleService;
	}

	protected Role getOr404(String resourceId) {
		return roleService.getOr404(resourceId);
	}

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<RoleDetailOutput> create(@RequestBody @Validated RoleAddInput input) {
		Role entity = getEntityFromAddInput(input);

		getService().save(entity);

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<RoleDetailOutput> update(@RequestBody @Validated RoleModifyInput input) {
		String resourceId = ObjUtil.getCertainFieldValue(input, "resourceId");

		Role originEntity = getOr404(resourceId);

		Role entity = getEntityFromModifyInput(input, originEntity);

		getService().updateById(entity);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<RoleDetailOutput> retrieve(String resource_id) {
		Role t = getOr404(resource_id);
		return new BaseRsp<>(getOutputFromEntity(t));
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		performBatchDelete(deleteReq.getIds());
		return new BaseRsp<>();
	}

	protected void performBatchDelete(List<String> ids) {
		getService().removeByIds(ids);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<RoleOutput>> list(RoleListReq<Role> listReq) {
		IPage<Role> page = getPage(listReq);

		List<RoleOutput> outputs = new ArrayList<>();
		for (Role record : page.getRecords()) {
			outputs.add(getListOutputFromEntity(record));
		}

		ListRsp<RoleOutput> listRsp = new ListRsp<>();
		listRsp.setList(outputs);
		listRsp.setTotal(page.getTotal());

		return new BaseRsp<>(listRsp);
	}

	@ApiOperation("获取课程列表")
	@GetMapping("/courses")
	public BaseRsp<List<CourseConfigOutput>> getSkillCourses(String resource_id,
			@ApiParam(allowableValues = "knowledge, skill") @NotBlank String field) {

		Role role = roleService.getOr404(resource_id);

		List<String> courseIds = getCourseIdsFromDb(role, field);

		List<CourseConfigOutput> outputList = getCourseConfigOutputList(courseIds);

		return new BaseRsp<>(outputList);
	}

	@ApiOperation("编排课程列表")
	@PostMapping("/courses")
	public BaseRsp<List<CourseConfigOutput>>
			setSkillCourses(@RequestBody @Validated CourseLayoutInput courseLayoutInput) {
		String roleId = courseLayoutInput.getResourceId();
		String field = courseLayoutInput.getField();

		Role role = roleService.getOr404(roleId);

		// 数据库中既有的课程编排
		List<String> oldIds = getCourseIdsFromDb(role, field);

		// 输入的课程编排
		List<String> inputIds = courseLayoutInput.getCourseIds();

		// 课程数量需要保持一致
		if (!SetUtils.isEqualSet(oldIds, inputIds)) {
			throw new BusinessException(RtksBusinessErrorCode.INPUT_COURSE_ERROR);
		}

		// 获取课程详情 同时去掉被删除的课程 必须在保存前执行
		List<CourseConfigOutput> outputList = getCourseConfigOutputList(inputIds);

		// 校验通过后保存
		updateRoleCourse(roleId, inputIds, field);

		return new BaseRsp<>(outputList);
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

	private void updateRoleCourse(String roleId, List<String> courseIds, String field) {
		Role roleToSave = new Role();

		roleToSave.setResourceId(roleId);

		String toSave = JsonUtil.toJsonString(courseIds);
		if (Fields.SKILL.equals(field)) {
			roleToSave.setSkillCourses(toSave);
		} else if (Fields.KNOWLEDGE.equals(field)) {
			roleToSave.setKnowledgeCourses(toSave);
		} else {
			throw new InvalidParamException("Invalid field:" + field);
		}

		roleService.updateById(roleToSave);
	}

	private List<String> getCourseIdsFromDb(Role role, String field) {
		String courseText = getCourseText(role, field);

		return JsonUtil.toJsonObjectArray(courseText, String.class);
	}

	/**
	 * 根据课程id列表获取课程的详细信息
	 * @param courseIds 课程id列表 注意 如果有课程找不到 courseIds数组会剔除这些课程id(12.28修改为不再删除课程)
	 */
	private List<CourseConfigOutput> getCourseConfigOutputList(List<String> courseIds) {
		List<CourseConfigOutput> courseConfigOutputList = new ArrayList<>();
		List<String> removedIds = new ArrayList<>();

		for (String courseId : courseIds) {
			CourseConfigOutput courseConfigOutput = new CourseConfigOutput();
			courseConfigOutput.setResourceId(courseId);

			CourseName course = courseNameService.getById(courseId);
			if (course == null) {
				removedIds.add(courseId);
				continue;
			}

			courseConfigOutput.setName(course.getName());
			courseConfigOutputList.add(courseConfigOutput);
		}

		courseIds.removeAll(removedIds);
		return courseConfigOutputList;
	}

	protected Role getEntityFromAddInput(RoleAddInput roleAddInput) {
		return getRoleFromInput(roleAddInput, null);
	}

	protected Role getEntityFromModifyInput(RoleModifyInput roleModifyInput, Role originEntity) {
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
			Level level = levelService.getOr404(levelId);

			if (Objects.equals(ConfigUtil.getContextProperty("roleRelatedSecondLevel"), "true")
					&& Objects.equals(level.getParent(), level.getFrame())) {
				throw new InvalidParamException("只允许在二级层级关联岗位");
			}
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
		role.setKnowledgeIdWeights(JsonUtil.toJsonString(input.getKnowledgeIdWeights()));

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

		role.setSkillIdWeights(JsonUtil.toJsonString(input.getSkillIdWeights()));

		return role;
	}

	public RoleDetailOutput getOutputFromEntity(Role role) {
		RoleDetailOutput output = outputFromRole(role, new RoleDetailOutput());

		List<String> taskIds = output.getTaskIds();
		List<Task> taskList = getRoleRelatedObjects(taskIds, taskService);
		output.setTaskDataList(taskList);

		List<String> knowledgeIds = output.getKnowledgeIds();
		List<Knowledge> knowledgeList = getRoleRelatedObjects(knowledgeIds, knowledgeService);
		List<SkillKnowledgeWeight> knowledgeWeights = JsonUtil
				.toJsonObjectArray(role.getKnowledgeIdWeights(), SkillKnowledgeWeight.class);
		output.setKnowledgeIdWeights(knowledgeWeights);

		output.setKnowledgeDataList(knowledgeList);

		List<String> skillIds = output.getSkillIds();
		List<Skill> skillList = getRoleRelatedObjects(skillIds, skillService);
		output.setSkillDataList(skillList);
		List<SkillKnowledgeWeight> skillWeights = JsonUtil
				.toJsonObjectArray(role.getSkillIdWeights(), SkillKnowledgeWeight.class);
		output.setSkillIdWeights(skillWeights);

		List<String> abilityIds = output.getAbilityIds();
		List<Ability> abilityList = getRoleRelatedObjects(abilityIds, abilityService);
		output.setAbilityDataList(abilityList);

		return output;
	}

	protected RoleOutput getListOutputFromEntity(Role role) {
		return outputFromRole(role, new RoleOutput());
	}

	protected IPage<Role> getPage(RoleListReq<Role> listReq) {
		String frameId;
		if (singleFrame) {
			// 单框架模式下 忽略所属框架筛选条件
			frameId = null;
		} else {
			frameId = listReq.getFrameId();
		}
		return roleService.listRoles(listReq.toPageParam(), listReq.getKeyword(), frameId);
	}

	private <T extends RoleOutput> T outputFromRole(Role role, T output) {
		output.setResourceId(role.getResourceId());
		output.setName(role.getName());
		output.setDescription(role.getDescription());

		String levelId = role.getLevelId();
		String fullPath = levelService.getFullPathById(levelId);
		if (fullPath == null) {
			levelId = null;
		}
		output.setLevelId(levelId);
		output.setLevelName(fullPath);

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

		// 更新时字段无变化，跳过该字段
		if (isModify && originIdStr.equals(idsStr)) {
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
