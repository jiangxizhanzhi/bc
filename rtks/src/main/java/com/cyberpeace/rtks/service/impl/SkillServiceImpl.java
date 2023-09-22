package com.cyberpeace.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionTemplateMapper;
import com.cyberpeace.cr.crbase.service.context.mission.CrTaskScore;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWithBT;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
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
import com.cyberpeace.rtks.util.WeightedCalculation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
	private CrTaskMapper crTaskMapper;

	@Autowired
	private SkillTrainingMapper skillTrainingMapper;

	@Autowired
	private MissionTemplateMapper missionTemplateMapper;

	private static final Integer UNSUBMITTED = 0;

	private static final Integer FINISHED = 50;

	private static final Integer FULL_MARKS = 100;

	/**
	 * 任务完成
	 */
	private static final Integer DONE = 1;

	@Override
	public ListRsp<SkillRes> page(PageReqBase skillListReq) {
		return PageUtil.getListResult(skillMapper.listSkill(skillListReq.toPageParam(), skillListReq));
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
	public void addMissionRelation(String skillId, List<IdWeight> missionTemplates) {
		if (CollectionUtils.isEmpty(missionTemplates)) {
			return;
		}
		// validate missionTemplateIds, make sure missionTemplate exist
		List<String> missionTemplateIds = missionTemplates.stream().map(IdWeight::getResourceId)
				.collect(Collectors.toList());
		List<String> validMissionTemplateIds = missionTemplateMapper
				.validateIds(missionTemplateIds);

		List<SkillMissionRelation> relations = Lists.newArrayList();
		for (IdWeight idWeight : missionTemplates) {
			// validation before insertion
			if (!validMissionTemplateIds.contains(idWeight.getResourceId())) {
				continue;
			}
			SkillMissionRelation relation = new SkillMissionRelation();
			relation.setSkillId(skillId);
			relation.setMissionTemplateId(idWeight.getResourceId());
			relation.setWeight(idWeight.getWeight());
			relations.add(relation);
		}

		if (relations.isEmpty()) {
			return;
		}

		skillMissionRelationMapper.insertBatch(relations);
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
		List<String> missionTemplateIds = missionWeights.stream().map(IdWeight::getResourceId)
				.collect(Collectors.toList());
		List<CrTaskScore> crTaskScoreList = crTaskMapper
				.getScoreByMissionTemplateIdAndUserId(userId, missionTemplateIds);
		Map<String, CrTaskScore> scoreMap = Maps.newHashMap();
		crTaskScoreList.forEach((score) -> scoreMap.put(score.getMissionTemplateId(), score));
		// calculate
		WeightedCalculation calculation = new WeightedCalculation();
		for (IdWeight mission : missionWeights) {
			CrTaskScore crTaskScore = scoreMap.get(mission.getResourceId());
			if (crTaskScore == null) {
				calculation.feed(mission.getWeight(), 0.0);
			} else {
				calculation.feed(mission.getWeight(), crTaskScore.getPercent());
			}
		}
		return calculation.solve();
	}

	@Override
	public void clearAll() {
		skillMapper.clearAll();
		skillMissionRelationMapper.clearAll();
	}

	@Override
	public ListRsp<SkillProcessRes> pageSkillListByRoleId(PageReqBase pageReqBase, String roleId) {

		// page skill
		Page page = pageReqBase.toPageParam();
		IPage<SkillResBase> pageRes = skillMapper.pageByRoleId(page, roleId);
		IPage<SkillProcessRes> result = pageRes
				.convert(skillResBase -> BeanUtils.copy(skillResBase, SkillProcessRes.class));
		List<String> skillIds = result.getRecords().stream().map(SkillProcessRes::getResourceId)
				.collect(Collectors.toList());
		if (skillIds.isEmpty()) {
			return PageUtil.getListResult(result);
		}
		// get skill-mission rel
		List<SkillAndMission> skillAndMissions = skillMissionRelationMapper
				.batchSelectBySkillIds(skillIds);
		List<String> missionIds = skillAndMissions.stream().map(SkillAndMission::getMissionId)
				.distinct().collect(Collectors.toList());

		// get mission score, scene name, mission title
		Map<String, MissionTemplateRes> missionMap = Maps.newHashMap();
		Map<String, CrTaskScore> scoreMap = Maps.newHashMap();
		if (!missionIds.isEmpty()) {
			List<MissionTemplateRes> missionResList = missionTemplateMapper
					.listWithBusiness(missionIds);
			String userId = UserInfoContext.getUserContext().getUserId();
			List<CrTaskScore> crTaskScoreList = crTaskMapper
					.getScoreByMissionTemplateIdAndUserId(userId, missionIds);
			for (MissionTemplateRes missionTemplateRes : missionResList) {
				missionMap.put(missionTemplateRes.getResourceId(), missionTemplateRes);
			}
			for (CrTaskScore crTaskScore : crTaskScoreList) {
				scoreMap.put(crTaskScore.getMissionTemplateId(), crTaskScore);
			}
		}
		// map skill - mission
		Map<String, List<String>> skillMissionMap = createSkillMissionMap(skillAndMissions);
		String skillId;
		// fill result
		for (SkillProcessRes skillProcess : result.getRecords()) {
			skillId = skillProcess.getResourceId();
			if (!skillMissionMap.containsKey(skillId)) {
				continue;
			}
			Integer sum = 0;
			for (String missionId : skillMissionMap.get(skillId)) {
				MissionProcessBo missionProcessBo = createMissionProcessBo(missionId, missionMap,
						scoreMap);
				skillProcess.getMissions().add(missionProcessBo);
				sum += missionProcessBo.getProcess();
			}
			skillProcess.setProcess(MathUtils.roundDivide(sum, skillProcess.getMissions().size()));
		}

		return PageUtil.getListResult(result);
	}

	@Override
	public ListRsp<SkillPagePath> pageSkillPathByRoleId(PageReqBase pageReqBase, String roleId) {
		// page skill
		Page page = pageReqBase.toPageParam();
		IPage<SkillResBase> pageRes = skillMapper.pageByRoleId(page, roleId);
		IPage<SkillPagePath> result = pageRes
				.convert(skillResBase -> BeanUtils.copy(skillResBase, SkillPagePath.class));
		List<String> skillIds = result.getRecords().stream().map(SkillPagePath::getResourceId)
				.collect(Collectors.toList());
		if (skillIds.isEmpty()) {
			return PageUtil.getListResult(result);
		}
		// get skill-mission rel
		List<SkillAndMission> skillAndMissions = skillMissionRelationMapper
				.batchSelectBySkillIds(skillIds);
		List<String> missionIds = skillAndMissions.stream().map(SkillAndMission::getMissionId)
				.distinct().collect(Collectors.toList());
		Map<String, List<String>> skillMissionMap = createSkillMissionMap(skillAndMissions);
		// get missions and template_ids
		if (missionIds.isEmpty()) {
			return PageUtil.getListResult(result);
		}
		List<MissionWithBT> missionWithBts = missionTemplateMapper.batchGetWithBusiness(missionIds);
		Set<String> businessTemplateIds = missionWithBts.stream()
				.map(MissionWithBT::getBusinessTemplateId).collect(Collectors.toSet());

		String userId = UserInfoContext.getUserContext().getUserId();
		// get single training
		List<TrainingRaw> singles = skillTrainingMapper
				.getSingleByBusinessTemplateIdsAndUserId(userId, businessTemplateIds);
		// get available team training
		List<TrainingRaw> teams = skillTrainingMapper.getTeamByBusinessTemplateIdsAndUserId(userId,
				businessTemplateIds);
		// get available rb training
		List<TrainingRaw> rbs = skillTrainingMapper.getRbByBusinessTemplateIdsAndUserId(userId,
				businessTemplateIds);
		// map mission-training(random one)
		Map<String, MissionWithBT> missionBtMap = Maps.newHashMap();
		missionWithBts.forEach(missionWithBt -> {
			missionBtMap.put(missionWithBt.getResourceId(), missionWithBt);
		});
		Map<String, TrainingRaw> btTrainingMap = Maps.newHashMap();
		mapTrainings(btTrainingMap, singles,
				BusinessType.TRAINING_SINGLE.getCode());
		mapTrainings(btTrainingMap, teams, BusinessType.TRAINING_TEAM.getCode());
		mapTrainings(btTrainingMap, rbs, BusinessType.RED_VS_BLUE.getCode());
		String skillId;
		for (SkillPagePath skill : result.getRecords()) {
			skillId = skill.getResourceId();
			skillMissionMap.get(skillId).forEach(missionId -> {
				MissionPathPageBo missionPathPageBo = createMissionPathPageBo(missionId,
						missionBtMap, btTrainingMap);
				skill.getMissions().add(missionPathPageBo);
			});
		}

		return PageUtil.getListResult(result);
	}

	private MissionPathPageBo createMissionPathPageBo(String missionId,
			Map<String, MissionWithBT> missionBtMap, Map<String, TrainingRaw> btTrainingMap) {
		MissionPathPageBo missionPathPageBo = new MissionPathPageBo();
		missionPathPageBo.setResourceId(missionId);
		MissionWithBT mission = missionBtMap.get(missionId);
		if (mission == null) {
			return missionPathPageBo;
		}
		missionPathPageBo.setTitle(mission.getTitle());
		missionPathPageBo.setSceneName(mission.getSceneName());
		missionPathPageBo.setType(mission.getType());
		missionPathPageBo.setScore(mission.getScore());

		TrainingRaw training = btTrainingMap.get(mission.getBusinessTemplateId());
		if (training == null) {
			missionPathPageBo.setAvailable(false);
		} else {
			missionPathPageBo.setAvailable(true);
			missionPathPageBo.setRoleGroup(training.getRoleGroup());
			missionPathPageBo.setTrainingId(training.getTrainingId());
			missionPathPageBo.setTrainingType(training.getTrainingType());
		}
		return missionPathPageBo;
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

	private MissionProcessBo createMissionProcessBo(String missionId,
			Map<String, MissionTemplateRes> missionMap, Map<String, CrTaskScore> scoreMap) {
		MissionTemplateRes mission = missionMap.get(missionId);
		CrTaskScore score = scoreMap.get(missionId);
		MissionProcessBo missionProcessBo = new MissionProcessBo();
		missionProcessBo.setResourceId(missionId);
		missionProcessBo.setTitle(mission.getTitle());
		missionProcessBo.setSceneName(mission.getSceneName());
		Integer process;
		if (score == null) {
			process = UNSUBMITTED;
		} else if (score.getScore() >= score.getFull()) {
			process = FULL_MARKS;
		} else {
			process = FINISHED;
		}
		missionProcessBo.setProcess(process);
		missionProcessBo
				.setStatus(process.intValue() == UNSUBMITTED.intValue() ? UNSUBMITTED : DONE);
		return missionProcessBo;
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
