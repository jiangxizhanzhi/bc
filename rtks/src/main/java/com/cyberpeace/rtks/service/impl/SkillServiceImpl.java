package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.context.mission.CrTaskScore;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.entity.SkillMissionRelation;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMissionRelationMapper;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.MissionRes;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillSimpleRes;
import com.cyberpeace.rtks.util.WeightedCalculation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	@Autowired
	private SkillMapper skillMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private SkillMissionRelationMapper skillMissionRelationMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Override
	public ListRsp<SkillRes> page(PageReqBase skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);
		// fill missions
		for (SkillRes skillRes : result.getRecords()) {
			skillRes.setMissionList(skillMissionRelationMapper
					.batchSelectMissionResBySkillId(skillRes.getResourceId()));
		}
		return PageUtil.getListResult(result);
	}

	@Override
	public ListRsp<SkillSimpleRes> pageSimple(PageReqBase reqBase) {
		Page page = reqBase.toPageParam();

		IPage<SkillSimpleRes> result = skillMapper.pageSimple(page, reqBase);
		return PageUtil.getListResult(result);
	}

	@Override
	public SkillRes getSkillByResourceId(String skillId) {
		Skill skill = skillMapper.selectById(skillId);
		if (skill == null) {
			throw new BusinessException(RtksBusinessErrorCode.SKILL_NOT_EXIST);
		}
		// 查询关联任务，时间倒序
		List<MissionRes> missionResList = skillMissionRelationMapper
				.batchSelectMissionResBySkillId(skillId);

		SkillRes skillRes = BeanUtils.copy(skill, SkillRes.class);
		skillRes.setMissionList(missionResList);
		return skillRes;
	}

	@Override
	public Skill getOr404(String skillId) {
		Skill skill = getById(skillId);
		if (skill == null) {
			log.error("skill not exist！skillId=[{}]", skillId);
			throw new BusinessException(RtksBusinessErrorCode.SKILL_NOT_EXIST);
		}
		return skill;
	}

	@Override
	public void addMissionRelation(String skillId, List<IdWeight> missionTemplates) {
		if (missionTemplates.isEmpty()) {
			return;
		}
		List<SkillMissionRelation> relations = Lists.newArrayList();
		for (IdWeight idWeight : missionTemplates) {
			SkillMissionRelation relation = new SkillMissionRelation();
			relation.setSkillId(skillId);
			relation.setMissionTemplateId(idWeight.getId());
			relation.setWeight(idWeight.getWeight());
			relations.add(relation);
		}
		// 插入前检查是否存在任务。 todo
		skillMissionRelationMapper.insertBatch(relations);
	}

	@Override
	public void deleteMissionRelation(String skillId, List<String> missionTemplateIds) {
		skillMissionRelationMapper.batchDeleteBySkillAndMissions(skillId, missionTemplateIds);
	}

	@Override
	public void checkResourceId(String resourceId) {
		CrCommonValidator.validateIdLen(resourceId, RtksBusinessErrorCode.INVALID_SKILL_ID);
		if (skillMapper.existByResourceId(resourceId)) {
			throw new BusinessException(RtksBusinessErrorCode.SKILL_ID_OCCUPIED);
		}
	}

	@Override
	public void updateMissionRelations(String skillId, List<IdWeight> missions) {
		List<SkillMissionRelation> relations = skillMissionRelationMapper.batchSelect(skillId);
		// 删除的关联
		List<String> deleted = Lists.newArrayList();
		// 编辑权重的关联
		List<SkillMissionRelation> modified = Lists.newArrayList();

		boolean flag;
		int i;
		IdWeight idWeight;
		for (SkillMissionRelation relation : relations) {
			flag = false;
			i = 0;
			while (i < missions.size()) {
				idWeight = missions.get(i);
				if (relation.getMissionTemplateId().equals(idWeight.getId())) {
					flag = true;
					// 编辑了权重的
					if (!relation.getWeight().equals(idWeight.getWeight())) {
						relation.setWeight(idWeight.getWeight());
						modified.add(relation);
					}
					// 去掉既存的，剩下的就是新增的
					missions.remove(i);
					break;
				} else {
					i++;
				}
			}
			// 没有在新列表中找到，删除
			if (!flag) {
				deleted.add(relation.getMissionTemplateId());
			}
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				addMissionRelation(skillId, missions);
				deleteMissionRelation(skillId, deleted);
				for (SkillMissionRelation relation : modified) {
					skillMissionRelationMapper.updateById(relation);
				}
			}
		});
	}

	@Override
	public void clearMissionRel(List<String> skillIds) {
		if (skillIds.isEmpty()) {
			return;
		}
		skillMissionRelationMapper.clearBySkillIds(skillIds);
	}

	@Override
	public Double calculateScore(String skillId, String userId) {
		List<IdWeight> missionWeights = skillMissionRelationMapper.listBySkillId(skillId);
		if (missionWeights.isEmpty()) {
			return 0.0;
		}
		// read task scores
		List<String> missionTemplateIds = missionWeights.stream().map(IdWeight::getId)
				.collect(Collectors.toList());
		List<CrTaskScore> crTaskScoreList = crTaskMapper
				.getScoreByMissionTemplateIdAndUserId(userId, missionTemplateIds);
		Map<String, CrTaskScore> scoreMap = Maps.newHashMap();
		crTaskScoreList.forEach((score) -> {
			scoreMap.put(score.getMissionTemplateId(), score);
		});
		// calculate
		WeightedCalculation calculation = new WeightedCalculation();
		for (IdWeight mission : missionWeights) {
			CrTaskScore crTaskScore = scoreMap.get(mission.getId());
			if (crTaskScore == null) {
				calculation.feed(mission.getWeight(), 0.0);
			} else {
				calculation.feed(mission.getWeight(), crTaskScore.getPercent());
			}
		}
		return calculation.solve();
	}

	public Integer calculateProcess(List<CourseStudyProcessBo> processBos,
			List<RelatedCourse> relatedCourseList) {
		Map<String, Integer> map = Maps.newHashMap();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			map.put(relatedCourse.getResourceId(), relatedCourse.getWeight());
		}

		Double sumWeight = Double.valueOf(0);
		Double sumProcess = Double.valueOf(0);

		for (CourseStudyProcessBo courseStudyProcessBo : processBos) {
			sumProcess += MathUtils.mul(courseStudyProcessBo.getProcess(),
					Double.valueOf(map.get(courseStudyProcessBo.getCourseId())));
			sumWeight += map.get(courseStudyProcessBo.getCourseId());
		}
		if (sumWeight <= 0.0) {
			return 0;
		} else {
			return MathUtils.ceilDivide(sumProcess, sumWeight);
		}
	}

}
