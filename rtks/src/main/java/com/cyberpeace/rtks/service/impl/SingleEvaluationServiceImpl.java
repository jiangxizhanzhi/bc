package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.RoleSkillRelationMapper;
import com.cyberpeace.rtks.dao.mapper.SingleEvaluationMapper;
import com.cyberpeace.rtks.service.SingleEvaluationService;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.bo.xiamen2c.*;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SingleEvaluationServiceImpl implements SingleEvaluationService {

	@Autowired
	private SingleEvaluationMapper singleEvaluationMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private SkillService skillService;

	@Autowired
	private RoleSkillRelationMapper roleSkillRelationMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public ListRsp<EvaluationSkillBo> listSkill(Page page, String roleId) {
		// get skills
		IPage<EvaluationSkillBo> skillBoPage = singleEvaluationMapper.listSkills(page, roleId);
		if (skillBoPage.getRecords().size() == 0) {
			return PageUtil.getListResult(skillBoPage);
		}
		List<String> skillIds = skillBoPage.getRecords().stream().map(EvaluationSkillBo::getSkillId)
				.collect(Collectors.toList());
		// count missions and users
		List<SkillMissionUserContext> contextList = singleEvaluationMapper
				.getTrainingStatisticsBySkillIds(skillIds);
		Map<String, SkillMissionUserContext> skillMap = StreamApiUtils.toMap(contextList,
				SkillMissionUserContext::getSkillId, context -> context);
		for (EvaluationSkillBo skillBo : skillBoPage.getRecords()) {
			SkillMissionUserContext context = skillMap.get(skillBo.getSkillId());
			if (context != null) {
				skillBo.setMissionCount(context.getMissionCount());
				skillBo.setTraineeCount(context.getTraineeCount());
			}
		}
		return PageUtil.getListResult(skillBoPage);
	}

	@Override
	public EvaluationSkillDetail getSkillDetail(String skillId) {
		EvaluationSkillDetail detail = new EvaluationSkillDetail(skillId);
		// get related missions
		List<MissionIdAndTitle> missions = singleEvaluationMapper.listMissionBySkillId(skillId);
		if (missions.isEmpty()) {
			return detail;
		}
		List<String> missionIds = missions.stream().map(MissionIdAndTitle::getResourceId)
				.collect(Collectors.toList());
		// get trainees
		List<String> userIds = singleEvaluationMapper.getTraineeIdsByMissionIds(missionIds);
		if (!userIds.isEmpty()) {
			// get trainee names
			Map<String, String> userNameMap = remoteUserService.getIdNameMap(userIds);
			// make result
			for (Map.Entry<String, String> user : userNameMap.entrySet()) {
				// calculate score
				Double score = skillService.calculateScore(skillId, user.getKey());
				detail.addTrainee(user.getValue(), MathUtils.roundDouble2Int(score));
			}
		}
		detail.setMissionNames(
				missions.stream().map(MissionIdAndTitle::getTitle).collect(Collectors.toList()));
		detail.setMissionCount(detail.getMissionNames().size());
		detail.setTraineeCount(detail.getTrainees().size());

		return detail;
	}

	@Override
	public ListRsp<EvaluationTraineeBo> listTrainee(Page page, String roleId) {
		// get trainees
		IPage<EvaluationTraineeBo> traineePage = singleEvaluationMapper.listTrainees(page, roleId);
		if (traineePage.getRecords().size() == 0) {
			return PageUtil.getListResult(traineePage);
		}
		List<String> userIds = traineePage.getRecords().stream().map(EvaluationTraineeBo::getUserId)
				.collect(Collectors.toList());
		// get skills
		List<String> roleIds = traineePage.getRecords().stream().map(EvaluationTraineeBo::getRoleId)
				.collect(Collectors.toList());
		List<RoleSkillSimple> roleSkillRelations = roleSkillRelationMapper
				.getSkillsByRoleIds(roleIds);
		Map<String, List<RoleSkillSimple>> roleSkillMap = StreamApiUtils.groupBy(roleSkillRelations,
				RoleSkillSimple::getRoleId);
		// get username
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);
		// make result
		String userId;
		for (EvaluationTraineeBo traineeBo : traineePage.getRecords()) {
			userId = traineeBo.getUserId();
			// set name
			traineeBo.setUserName(idNameMap.get(userId));
			// get skill score
			List<RoleSkillSimple> skillList = roleSkillMap.getOrDefault(traineeBo.getRoleId(),
					Lists.newArrayList());
			for (RoleSkillSimple skill : skillList) {
				// calculate score
				Double skillScore = skillService.calculateScore(skill.getResourceId(), userId);
				Integer score = MathUtils.roundDouble2Int(skillScore);
				// 60分通过
				if (score >= 60) {
					traineeBo.addQualified(skill.getContent(), score);
				} else {
					traineeBo.addUnqualified(skill.getContent(), score);
				}
			}
			// count
			traineeBo.count();
		}
		return PageUtil.getListResult(traineePage);
	}

	@Override
	public List<RoleSimple> listRoles() {
		return roleMapper.getAllRoleSimples();
	}
}
