package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.controller.res.KnowledgeTreeRes;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillTreeRes;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.service.FrameService;
import com.cyberpeace.rtks.service.LevelService;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.TrainingPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

	@ApiOperation("技能体系")
	@GetMapping("/skillTree")
	public BaseRsp<List<SkillTreeRes>> skillTree(@ApiParam(name = "skillSystemId", value = "技能体系id",
			required = false) String skillSystemId) {

		List<SkillTreeRes> skillTreeRes = trainingPlanService.getSkillTree(skillSystemId);

		return new BaseRsp<>(skillTreeRes);
	}

	@ApiOperation("知识体系")
	@GetMapping("/knowledgeTree")
	public BaseRsp<List<KnowledgeTreeRes>> knowledgeTree() {
		List<KnowledgeTreeRes> knowledgeTreeRes = trainingPlanService.getKnowledgeTree();

		return new BaseRsp<>(knowledgeTreeRes);
	}

	@ApiOperation("职业框架管理")
	@GetMapping("/tree")
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
					LOG.info("未检索到相应岗位");
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
				}
			}
		}

		LevelTreeOutput frame = frameService.getTree(resource_id);
		trainingPlanService.parseTree(frame);

		return new BaseRsp<>(frame);
	}

	@ApiOperation("学习路径")
	@GetMapping("/learning-path")
	public BaseRsp<RoleDetailRes> learningPath(
			@ApiParam(name = "resource_id", value = "岗位id", required = true) String resource_id) {
		RoleDetailRes roleOutput = trainingPlanService.getLearningPathTree(resource_id,
				RoleDetailRes.RELATED_COURSE_COURSE);

		return new BaseRsp<RoleDetailRes>(roleOutput);
	}

	@ApiOperation("人才能力图谱（获取技能和知识点关联的课程内容(不包括课时等信息)）")
	@GetMapping("/roleMap")
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

	@ApiOperation("分页获取知识点以及课程")
	@GetMapping("/getKnowledgeCourseList")
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
	public BaseRsp<RoleDetailRes> getRelatedCourseList() {// @ApiParam(name = "course_type", value =
															// "查询课程类型(skill/knowledge)", required =
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleDetailRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {

			RoleDetailRes data = trainingPlanService.getRoleOutputByRoleId(existRoleId,
					RoleDetailRes.RELATED_COURSE_STUDY);

			String percent = rtksStatisticService.roleMatchPercent(existRoleId,
					UserInfoContext.getUserContext().getUserId());

			data.setPercent(percent);
			rsp.setData(data);
		}

		return rsp;
	}

	@ApiOperation("查询个人能力评估")
	@GetMapping("/getRoleDetailRes")
	public BaseRsp<RoleDetailRes> getRoleDetailRes() {// @ApiParam(name = "course_type", value =
		// "查询课程类型(skill/knowledge)", required =
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleDetailRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			RoleDetailRes data = trainingPlanService.getRoleDetailResByRoleId(existRoleId);

			String percent = rtksStatisticService.roleMatchPercent(existRoleId,
					UserInfoContext.getUserContext().getUserId());

			data.setPercent(percent);
			rsp.setData(data);
		}

		return rsp;
	}
}
