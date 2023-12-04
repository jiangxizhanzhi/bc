package com.cyberpeace.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.entity.SkillMissionRelation;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMissionRelationMapper;
import com.cyberpeace.rtks.dao.mapper.SkillTrainingMapper;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.bo.skill.*;
import com.cyberpeace.rtks.util.RtksCommonValidator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	private SkillTrainingMapper skillTrainingMapper;

	private static final Integer UNSUBMITTED = 0;

	private static final Integer FINISHED = 50;

	private static final Integer FULL_MARKS = 100;

	/**
	 * 任务完成
	 */
	private static final Integer DONE = 1;

	@Override
	public ListRsp<SkillRes> page(PageReqBase skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);
		// fill missions
		Set<String> skillIds = result.getRecords().stream().map(SkillRes::getResourceId)
				.collect(Collectors.toSet());
		if (!skillIds.isEmpty()) {
			List<MissionRes> missionResList = skillMissionRelationMapper
					.batchSelectMissionResNameBySkillIds(skillIds);
			Map<String, List<MissionRes>> missionMap = StreamApiUtils.groupBy(missionResList,
					MissionRes::getSkillId);
			for (SkillRes skillRes : result.getRecords()) {
				skillRes.setMissions(missionMap.getOrDefault(skillRes.getResourceId(), Lists.newArrayList()));
			}
		}

		return PageUtil.getListResult(result);
	}

	@Override
	public ListRsp<SkillSimpleRes> pageSimple(PageReqBase reqBase) {
		return PageUtil.getListResult(skillMapper.pageSimple(reqBase.toPageParam(), reqBase));
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
		skillRes.setMissions(missionResList);
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
	public void deleteMissionRelation(String skillId, List<String> missionTemplateIds) {
		if (missionTemplateIds.isEmpty()) {
			return;
		}
		skillMissionRelationMapper.batchDeleteBySkillAndMissions(skillId, missionTemplateIds);
	}

	@Override
	public void checkResourceId(String resourceId) {
		RtksCommonValidator.validateIdLen(resourceId, RtksBusinessErrorCode.INVALID_SKILL_ID);
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
				if (relation.getMissionTemplateId().equals(idWeight.getResourceId())) {
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
	public void clearAll() {
		skillMapper.clearAll();
		skillMissionRelationMapper.clearAll();
	}

	private void mapTrainings(Map<String, TrainingRaw> map, List<TrainingRaw> trainingRaws,
			Integer type) {
		trainingRaws.forEach(training -> {
			training.setTrainingType(type);
			map.put(training.getBusinessTemplateId(), training);
		});
	}

	private Map<String, List<String>> createSkillMissionMap(List<SkillAndMission> skillAndMissions) {
		Map<String, List<String>> skillMissionMap = Maps.newHashMap();
		String skillId;
		for (SkillAndMission skillAndMission : skillAndMissions) {
			skillId = skillAndMission.getSkillId();
			skillMissionMap.computeIfAbsent(skillId, item -> new ArrayList<>());
			skillMissionMap.get(skillId).add(skillAndMission.getMissionId());
		}
		return skillMissionMap;
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
