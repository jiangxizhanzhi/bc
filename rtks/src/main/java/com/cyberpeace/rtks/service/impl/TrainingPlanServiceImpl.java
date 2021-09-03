package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DataTransferUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.RoleController;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.*;
import com.cyberpeace.rtks.controller.role.RoleDetailOutput;
import com.cyberpeace.rtks.controller.role.RoleOutput;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.TrainingPlanService;
import com.cyberpeace.rtks.service.bo.CourseKnowledgeBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class TrainingPlanServiceImpl implements TrainingPlanService {

	@Autowired
	private SkillService skillService;

	@Autowired
	private RoleController roleController;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private KnowledgeService knowledgeService;

	public void parseTreeNode(LevelTreeOutput frame,
			Map<String, List<RoleDetailOutput>> roleListMap) {
		for (int index = 0; index < frame.getChildren().size(); index++) {
			LevelTreeOutput element = frame.getChildren().get(index);

			if (element.getChildren() == null) {
				// 处理叶子结点
				element.setRoleDetailList(roleListMap.get(element.getResourceId()));
			} else {
				parseTreeNode(element, roleListMap);
			}
		}
	}

	@Override
	public void parseTree(LevelTreeOutput frame) {
		List<Role> roleList = roleMapper.selectRoleByFrameId(frame.getResourceId());
		List<RoleDetailOutput> roleDetailOutputList = Lists.newArrayList();

		for (Role role : roleList) {
			RoleDetailOutput detailOutputFromRole = roleController.getOutputFromEntity(role);
			roleDetailOutputList.add(detailOutputFromRole);
		}
		// 以层级对角色进行分组
		Map<String, List<RoleDetailOutput>> roleListMap = roleDetailOutputList.stream()
				.collect(Collectors.groupingBy(RoleOutput::getLevelId));

		parseTreeNode(frame, roleListMap);
	}

	@Override
	public Map<String, List<SkillRes>> getSkillTree() {

		List<SkillRes> skillResList = skillService.getAllSkills();

		Map<String, List<SkillRes>> skillResMap = skillResList.stream()
				.collect(Collectors.groupingBy(SkillRes::getCategory));
		return skillResMap;
	}

	@Override
	public RoleDetailRes getRoleOutputByRoleId(String resource_id, String type) {
		Role role = roleMapper.selectById(resource_id);
		RoleDetailOutput roleDetailOutput = roleController.getOutputFromEntity(role);
		RoleDetailRes roleDetailRes = BeanUtils.deepCopy(roleDetailOutput, RoleDetailRes.class);
		roleDetailRes.setCategory(roleDetailOutput.getCategoryName());
		// 获取知识点的课程列表
		List<Knowledge> knowledgeDataList = roleDetailOutput.getKnowledgeDataList();
		List<KnowledgeRes> knowledgeResList = BeanUtils.deepCopyList(knowledgeDataList,
				KnowledgeRes.class);

		knowledgeService.checkKonwledgeRes(knowledgeResList, type);
		roleDetailRes.setKnowledgeResDataList(knowledgeResList);

		// 获取技能的课程列表
		List<Skill> skillDataList = roleDetailOutput.getSkillDataList();
		List<SkillRes> skillResList = BeanUtils.deepCopyList(skillDataList, SkillRes.class);

		skillService.checkSkillRes(skillResList, type);
		roleDetailRes.setSkillResDataList(skillResList);

		return roleDetailRes;
	}

	@Override
	public ListRsp<KnowledgeRes> getKnowledgeCourseList(PageReqBase pageReqBase,
			String existRoleId) {
		Page page = pageReqBase.toPageParam();
		List<String> knowledgeIds = getKnowledgeIdsByRole(existRoleId);

		IPage<KnowledgeRes> result = knowledgeMapper.getKnowledgeListByIds(page, knowledgeIds);

		List<KnowledgeRes> knowledgeResList = result.getRecords();
		knowledgeService.checkKonwledgeRes(knowledgeResList, RoleDetailRes.RELATED_COURSE_ALL);

		return PageUtil.getListResult(result);
	}

	@Override
	public RoleDetailRes getLearningPathTree(String resource_id, String type) {
		Role role = roleMapper.selectById(resource_id);
		RoleDetailOutput roleDetailOutput = roleController.getOutputFromEntity(role);
		RoleDetailRes roleDetailRes = BeanUtils.deepCopy(roleDetailOutput, RoleDetailRes.class);

		List<Level> levelList = roleMapper.selectLevelsByRoleId(resource_id);
		roleDetailRes.setLevelTreeOutput(getLevelTree(levelList, role.getLevelId()));

		// 获取知识点的课程列表
		List<Knowledge> knowledgeDataList = roleDetailOutput.getKnowledgeDataList();
		List<KnowledgeRes> knowledgeResList = BeanUtils.deepCopyList(knowledgeDataList,
				KnowledgeRes.class);

		knowledgeService.checkKonwledgeRes(knowledgeResList, type);
		List<RelatedCourse> basicCourseList = Lists.newArrayList();
		for (KnowledgeRes knowledgeRes : knowledgeResList) {
			basicCourseList.addAll(knowledgeRes.getCourseList());
		}
		roleDetailRes.setBasicCourseList(basicCourseList);

		// 获取技能的课程列表
		List<Skill> skillDataList = roleDetailOutput.getSkillDataList();
		List<SkillRes> skillResList = BeanUtils.deepCopyList(skillDataList, SkillRes.class);

		skillService.checkSkillRes(skillResList, type);
		List<RelatedCourse> skillCourseList = Lists.newArrayList();
		for (SkillRes skillRes : skillResList) {
			skillCourseList.addAll(skillRes.getCourseList());
		}
		roleDetailRes.setSkillCourseList(skillCourseList);

		return roleDetailRes;
	}

	private LevelTreeOutput getLevelTree(List<Level> levelList, String levelId) {
		LevelTreeOutput levelTreeOutput = new LevelTreeOutput();
		Level level = new Level();
		Map<String, Level> levelMap = Maps.newHashMap();
		List<Level> newlevelList = Lists.newArrayList();

		for (Level level1 : levelList) {
			if (StringUtils.equals(level1.getResourceId(), levelId)) {
				level = level1;
				newlevelList.add(level);
				break;
			}

		}
		int index = 1;
		while (index < 10 && StringUtils.isNotBlank(level.getParent())) {
			levelMap.put(level.getParent(), level);

			for (Level level1 : levelList) {
				if (StringUtils.equals(level1.getResourceId(), level.getParent())) {

					level = level1;
					newlevelList.add(level);
					index++;
					break;
				}
			}
		}
		if (index >= 10 || index == 1) {
			throw new ResourceNotFoundException("层级" + levelId + "不合法");
		}

		for (Level level1 : newlevelList) {
			if (StringUtils.isBlank(level1.getParent())) {
				levelTreeOutput = getLevelTreeOutputFromLevel(level1);
			}
		}
		String childId = levelTreeOutput.getResourceId();
		while (levelMap.get(childId) != null) {
			List<LevelTreeOutput> children = Lists.newArrayList();
			children.add(
					getLevelTreeOutputFromLevel(levelMap.get(levelTreeOutput.getResourceId())));
			levelTreeOutput.setChildren(children);
			childId = levelMap.get(levelTreeOutput.getResourceId()).getResourceId();
		}
		return levelTreeOutput;
	}

	LevelTreeOutput getLevelTreeOutputFromLevel(Level frame) {
		LevelTreeOutput output = new LevelTreeOutput();

		output.setResourceId(frame.getResourceId());
		output.setName(frame.getName());
		output.setDescription(frame.getDescription());

		return output;
	}

	/**
	 * 根据岗位编号查询知识点编号
	 * @param roleId
	 * @return
	 */
	private List<String> getKnowledgeIdsByRole(String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			return Lists.newArrayList();
		}
		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
				String.class);
		return knowledgeIds;
	}

}