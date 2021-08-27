package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.rtks.controller.RoleController;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.controller.role.RoleDetailOutput;
import com.cyberpeace.rtks.controller.role.RoleOutput;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.TrainingPlanService;
import com.google.common.collect.Lists;

@Service
public class TrainingPlanServiceImpl implements TrainingPlanService {

	@Autowired
	private SkillService skillService;

	@Autowired
	private RoleController roleController;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private KnowledgeService knowledgeService;

	public void parseTreeNode(LevelTreeOutput frame, Map<String, List<RoleOutput>> roleListMap) {
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
		List<RoleOutput> roleDetailOutputList = Lists.newArrayList();

		for (Role role : roleList) {
			RoleOutput detailOutputFromRole = roleController.getOutputFromEntity(role);
			roleDetailOutputList.add(detailOutputFromRole);
		}
		// 以层级对角色进行分组
		Map<String, List<RoleOutput>> roleListMap = roleDetailOutputList.stream()
				.collect(Collectors.groupingBy(RoleOutput::getLevelId));

		parseTreeNode(frame, roleListMap);
	}

	@Override
	public Map<String, List<SkillRes>> getSkillTree() {
		List<SkillRes> skillResList = skillService.page(new SkillListReq()).getList();

		Map<String, List<SkillRes>> skillResMap = skillResList.stream()
				.collect(Collectors.groupingBy(SkillRes::getCategory));
		return skillResMap;
	}

	@Override
	public RoleDetailRes getRoleOutputByRoleId(String resource_id) {
		Role role = roleMapper.selectById(resource_id);
		RoleDetailOutput roleDetailOutput = roleController.getOutputFromEntity(role);
		RoleDetailRes roleDetailRes = BeanUtils.copy(roleDetailOutput, RoleDetailRes.class);

		// 获取知识点的课程列表
		List<Knowledge> knowledgeDataList = roleDetailOutput.getKnowledgeDataList();
		List<KnowledgeRes> knowledgeResList = BeanUtils.deepCopyList(knowledgeDataList,
				KnowledgeRes.class);

		knowledgeService.checkKonwledgeRes(knowledgeResList);
		roleDetailRes.setKnowledgeResDataList(knowledgeResList);

		// 获取技能的课程列表
		List<Skill> skillDataList = roleDetailOutput.getSkillDataList();
		List<SkillRes> skillResList = BeanUtils.deepCopyList(skillDataList, SkillRes.class);

		skillService.checkSkillRes(skillResList);
		roleDetailRes.setSkillResDataList(skillResList);

		return roleDetailRes;
	}

}