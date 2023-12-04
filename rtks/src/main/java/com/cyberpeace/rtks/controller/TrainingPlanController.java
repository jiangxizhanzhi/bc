package com.cyberpeace.rtks.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.skill.RelatedTrainingRes;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.res.KnowledgeTreeRes;
import com.cyberpeace.rtks.res.RoleDetailRes;
import com.cyberpeace.rtks.service.*;
import com.cyberpeace.rtks.service.bo.skill.MissionPathBo;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Api(tags = "TRKS-训练计划管理")
@RestController
@RequestMapping("/rtks/training-plan")
@RequiresAuthentication
public class TrainingPlanController {

	private static final Logger LOG = LoggerFactory.getLogger(TrainingPlanController.class);

	@Autowired
	private TrainingPlanService trainingPlanService;

	@Autowired
	private RtksStatisticService rtksStatisticService;

	@Autowired
	private LevelService levelService;

	@Autowired
	private FrameService frameService;

	@Autowired
	private SkillService skillService;

	@Autowired
	private RtksSituationService rtksSituationService;

	@ApiOperation("知识体系")
	@GetMapping("/knowledgeTree")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_KNOWLEDGE_COURSE,
			MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<List<KnowledgeTreeRes>> knowledgeTree() {
		List<KnowledgeTreeRes> knowledgeTreeRes = trainingPlanService.getKnowledgeTree();
		return new BaseRsp<>(knowledgeTreeRes);
	}

	@ApiOperation("职业框架管理")
	@GetMapping("/tree")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_KNOWLEDGE_COURSE,
			MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<LevelTreeOutput> tree(
			@ApiParam(name = "resource_id", value = "框架id", required = true) String resource_id) {
		// 如果前端没有获取框架，尝试获取默认角色的框架
		if (StringUtils.equals(resource_id, "-1")) {
			resource_id = "";
			String existRoleId = UserInfoContext.getUserContext().getRoleId();
			if (StringUtils.isNotBlank(existRoleId)) {
				try {
					Role role = trainingPlanService.getRoleByRoleId(existRoleId);
					if (role != null) {
						resource_id = levelService.getOr404(role.getLevelId()).getFrame();
					}

				} catch (Exception e) {
					// 未检索到相应岗位
					LOG.info("[TRKS]: No corresponding jobs found");
				}

			}
		}
		// 如果还是获取不到框架，从框架列表查询第一个框架返回
		if (StringUtils.isBlank(resource_id)) {
			List<Level> levelList = levelService.list();
			for (Level level : levelList) {

				if (StringUtils.isNotBlank(level.getFrame())) {
					resource_id = level.getFrame();
					break;
				} else {
					resource_id = level.getResourceId();
				}
			}
		}

		LevelTreeOutput frame = frameService.getTree(resource_id);
		trainingPlanService.parseTree(frame);

		return new BaseRsp<>(frame);
	}

	// 对应页面：岗位简介树状图
	@ApiOperation("学习路径")
	@GetMapping("/learning-path")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_KNOWLEDGE_COURSE,
			MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<RoleDetailRes> learningPath(
			@ApiParam(name = "resource_id", value = "岗位id", required = true) String resource_id) {
		RoleDetailRes roleOutput = trainingPlanService.getLearningPathTree(resource_id,
				RoleDetailRes.RELATED_COURSE_COURSE);

		return new BaseRsp<RoleDetailRes>(roleOutput);
	}

	@ApiOperation("人才能力图谱（获取技能和知识点关联的课程内容(不包括课时等信息)）")
	@GetMapping("/roleMap")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_KNOWLEDGE_COURSE,
			MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<RoleDetailRes> getRoleOutputByRoleId() {
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleDetailRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			RoleDetailRes data = trainingPlanService.getRoleOutputByRoleId(existRoleId,
					RoleDetailRes.RELATED_COURSE_COURSE);
			rsp.setData(data);
		}
		return rsp;
	}

	// 岗位评估-技能体系清单
	// 岗位简介-岗位学习路径
	@ApiOperation("分页获取知识点以及课程")
	@GetMapping("/getKnowledgeCourseList")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_KNOWLEDGE_COURSE,
			MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<ListRsp<KnowledgeRes>> page(@Validated PageReqBase pageReqBase) {
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<ListRsp<KnowledgeRes>> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			ListRsp<KnowledgeRes> data = trainingPlanService.getKnowledgeCourseList(pageReqBase,
					existRoleId);
			rsp.setData(data);
		}
		return rsp;
	}

	@ApiOperation("查询技能和知识点关联的课程学习情况")
	@GetMapping("/getRelatedCourseList")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_KNOWLEDGE_COURSE,
			MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<RoleDetailRes> getRelatedCourseList() {// @ApiParam(name = "course_type", value =
															// "查询课程类型(skill/knowledge)", required =
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleDetailRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {

			RoleDetailRes data = trainingPlanService.getRoleOutputByRoleId(existRoleId,
					RoleDetailRes.RELATED_COURSE_STUDY);

			String userId = UserInfoContext.getUserContext().getUserId();
			String userName = UserInfoContext.getUserContext().getName();
			String roleName = UserInfoContext.getUserContext().getRoleName();
			// 计算并记录匹配度
			Integer percent = rtksSituationService.recordSituation(userId, userName, existRoleId,
					roleName);
			data.setPercent(percent + "");
			rsp.setData(data);
		}

		return rsp;
	}

	@ApiOperation("查询个人能力评估")
	@GetMapping("/getRoleDetailRes")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_KNOWLEDGE_COURSE,
			MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<RoleDetailRes> getRoleDetailRes() {// @ApiParam(name = "course_type", value =
		// "查询课程类型(skill/knowledge)", required =
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleDetailRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			RoleDetailRes data = trainingPlanService.getRoleDetailResByRoleId(existRoleId);
			// 查询匹配度时，记录匹配度
			String userId = UserInfoContext.getUserContext().getUserId();
			String userName = UserInfoContext.getUserContext().getName();
			String roleName = UserInfoContext.getUserContext().getRoleName();
			Integer percent = rtksSituationService.recordSituation(userId, userName, existRoleId,
					roleName);
			data.setPercent(percent + "");
			rsp.setData(data);
		}

		return rsp;
	}

	/**
	 * 从训练路径中解析出全部当前岗位相关训练， 去重
	 * @param trainingPath 
	 * @return
	 */
	private List<RelatedTrainingRes> getAllRelatedTraining(List<MissionPathBo> trainingPath) {
		Set<String> trainingIds = Sets.newHashSet();
		List<RelatedTrainingRes> allRelated = Lists.newArrayList();
		for (MissionPathBo missionPathBo : trainingPath) {
			missionPathBo.getRelatedTrainings().forEach((trainingBo -> {
				if (!trainingIds.contains(trainingBo.getTrainingId())) {
					allRelated.add(BeanUtils.copy(trainingBo, RelatedTrainingRes.class));
					trainingIds.add(trainingBo.getTrainingId());
				}
			}));
		}
		return allRelated;
	}

}
