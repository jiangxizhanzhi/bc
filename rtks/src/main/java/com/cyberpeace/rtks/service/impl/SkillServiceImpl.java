package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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
import com.cyberpeace.common.util.StreamApiUtils;
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

/**
 * 技能Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ServiceImpl
 * @see SkillService
 * @since 1.0.0
 */
@Slf4j
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	/**
	 * 技能Mapper
	 *
	 * @see SkillMapper
	 */
	@Autowired
	private SkillMapper skillMapper;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 技能任务关系Mapper
	 *
	 * @see SkillMissionRelationMapper
	 */
	@Autowired
	private SkillMissionRelationMapper skillMissionRelationMapper;

	/**
	 * 靶场任务Mapper
	 *
	 * @see CrTaskMapper
	 */
	@Autowired
	private CrTaskMapper crTaskMapper;

	/**
	 * 技能训练Mapper
	 *
	 * @see SkillTrainingMapper
	 */
	@Autowired
	private SkillTrainingMapper skillTrainingMapper;

	/**
	 * 任务模板Mapper
	 *
	 * @see MissionTemplateMapper
	 */
	@Autowired
	private MissionTemplateMapper missionTemplateMapper;

	/**
	 * 未提交
	 *
	 * @see Integer
	 */
	private static final Integer UNSUBMITTED = 0;

	/**
	 * 完成
	 *
	 * @see Integer
	 */
	private static final Integer FINISHED = 50;

	/**
	 * 满分
	 *
	 * @see Integer
	 */
	private static final Integer FULL_MARKS = 100;

	/**
	 * 任务完成
	 *
	 * @see Integer
	 */
	private static final Integer DONE = 1;

	/**
	 * 分页
	 *
	 * @param skillListReq 技能列表请求体
	 * @return {@code ListRsp<SkillRes> }
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<SkillRes>
	 */
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

	/**
	 * 分页简单
	 *
	 * @param reqBase 请求体基础
	 * @return {@code ListRsp<SkillSimpleRes> }
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<SkillSimpleRes>
	 */
	@Override
	public ListRsp<SkillSimpleRes> pageSimple(PageReqBase reqBase) {
		return PageUtil.getListResult(skillMapper.pageSimple(reqBase.toPageParam(), reqBase));
	}

	/**
	 * 获取技能由资源id
	 *
	 * @param skillId 技能id
	 * @return {@code SkillRes }
	 * @throws
	 * @see String
	 * @see SkillRes
	 */
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

	/**
	 * 获取OR404
	 *
	 * @param skillId 技能id
	 * @return {@code Skill }
	 * @throws
	 * @see String
	 * @see Skill
	 */
	@Override
	public Skill getOr404(String skillId) {
		Skill skill = getById(skillId);
		if (skill == null) {
			log.error("skill not exist！skillId=[{}]", skillId);
			throw new BusinessException(RtksBusinessErrorCode.SKILL_NOT_EXIST);
		}
		return skill;
	}

	/**
	 * 新增任务关系
	 *
	 * @param skillId          技能id
	 * @param missionTemplates 任务模板
	 * @throws
	 * @see String
	 * @see List<IdWeight>
	 */
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

	/**
	 * 删除任务关系
	 *
	 * @param skillId            技能id
	 * @param missionTemplateIds 任务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	@Override
	public void deleteMissionRelation(String skillId, List<String> missionTemplateIds) {
		if (missionTemplateIds.isEmpty()) {
			return;
		}
		skillMissionRelationMapper.batchDeleteBySkillAndMissions(skillId, missionTemplateIds);
	}

	/**
	 * 检查资源id
	 *
	 * @param resourceId 资源id
	 * @throws
	 * @see String
	 */
	@Override
	public void checkResourceId(String resourceId) {
		CrCommonValidator.validateIdLen(resourceId, RtksBusinessErrorCode.INVALID_SKILL_ID);
		if (skillMapper.existByResourceId(resourceId)) {
			throw new BusinessException(RtksBusinessErrorCode.SKILL_ID_OCCUPIED);
		}
	}

	/**
	 * 更新任务关系
	 *
	 * @param skillId  技能id
	 * @param missions 任务
	 * @throws
	 * @see String
	 * @see List<IdWeight>
	 */
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

	/**
	 * 清除任务关联
	 *
	 * @param skillIds 技能id列表
	 * @throws
	 * @see List<String>
	 */
	@Override
	public void clearMissionRel(List<String> skillIds) {
		if (skillIds.isEmpty()) {
			return;
		}
		skillMissionRelationMapper.clearBySkillIds(skillIds);
	}

	/**
	 * 计算分数
	 *
	 * @param skillId 技能id
	 * @param userId  用户id
	 * @return {@code Double }
	 * @throws
	 * @see String
	 * @see String
	 * @see Double
	 */
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

	/**
	 * 清除全部
	 *
	 * @throws
	 */
	@Override
	public void clearAll() {
		skillMapper.clearAll();
		skillMissionRelationMapper.clearAll();
	}

	/**
	 * 分页技能列表由岗位id
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param roleId      岗位id
	 * @return {@code ListRsp<SkillProcessRes> }
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see ListRsp<SkillProcessRes>
	 */
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

	/**
	 * 分页技能路径由岗位id
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param roleId      岗位id
	 * @return {@code ListRsp<SkillPagePath> }
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see ListRsp<SkillPagePath>
	 */
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

	/**
	 * 创建任务路径分页Bo
	 *
	 * @param missionId     任务id
	 * @param missionBtMap  任务BT系列映射
	 * @param btTrainingMap BT系列训练映射
	 * @return {@code MissionPathPageBo }
	 * @throws
	 * @see String
	 * @see Map<String, MissionWithBT>
	 * @see Map<String, TrainingRaw>
	 * @see MissionPathPageBo
	 */
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

	/**
	 * 映射培训
	 *
	 * @param map          映射
	 * @param trainingRaws 训练原始
	 * @param type         类型
	 * @throws
	 * @see Map<String, TrainingRaw>
	 * @see List<TrainingRaw>
	 * @see Integer
	 */
	private void mapTrainings(Map<String, TrainingRaw> map, List<TrainingRaw> trainingRaws,
			Integer type) {
		trainingRaws.forEach(training -> {
			training.setTrainingType(type);
			map.put(training.getBusinessTemplateId(), training);
		});
	}

	/**
	 * 创建技能任务映射
	 *
	 * @param skillAndMissions 技能和任务
	 * @return {@code Map<String, List<String>> }
	 * @throws
	 * @see List<SkillAndMission>
	 * @see Map<String, List<String>>
	 */
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

	/**
	 * 创建任务过程Bo
	 *
	 * @param missionId  任务id
	 * @param missionMap 任务映射
	 * @param scoreMap   得分映射
	 * @return {@code MissionProcessBo }
	 * @throws
	 * @see String
	 * @see Map<String, MissionTemplateRes>
	 * @see Map<String, CrTaskScore>
	 * @see MissionProcessBo
	 */
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

	/**
	 * 计算过程
	 *
	 * @param processBos        处理 BOS
	 * @param relatedCourseList 相关课程列表
	 * @return {@code Integer }
	 * @throws
	 * @see List<CourseStudyProcessBo>
	 * @see List<RelatedCourse>
	 * @see Integer
	 */
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
