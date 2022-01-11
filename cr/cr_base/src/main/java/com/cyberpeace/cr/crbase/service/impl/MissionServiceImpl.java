package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.rsp.MissionSchemeTemplateRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeTemplateConfig;
import com.cyberpeace.cr.crbase.service.context.mission.TrainingManualTeam;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinqingrong
 */
@Service
@Slf4j
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private MissionTemplateMapper missionTemplateMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	@Autowired
	private MissionSchemeTemplateMapper missionSchemeTemplateMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private MissionCategoryMapper missionCategoryMapper;

	private Map<String, MissionCategory> categoryCache;

	@PostConstruct
	public void init() {
		List<MissionCategory> categories = missionCategoryMapper.selectAll();

		categoryCache = Maps.newHashMap();

		for (MissionCategory item : categories) {
			categoryCache.put(item.getResourceId(), item);
		}
	}

	@Override
	public MissionTemplate addMissionTemplate(MissionTemplate missionTemplate) {
		missionTemplateMapper.insert(missionTemplate);
		return missionTemplate;
	}

	@Override
	public MissionTemplate getByMissionTemplateId(String missionTemplateId) {
		return missionTemplateMapper.selectById(missionTemplateId);
	}

	@Override
	public void updateMissionTemplate(MissionTemplate missionTemplate) {
		missionTemplateMapper.updateById(missionTemplate);
	}

	@Override
	public Mission getById(String missionId) {
		return missionMapper.selectById(missionId);
	}

	@Override
	public List<Mission> batchGet(List<String> resourceIds) {
		List<Mission> ret = Lists.newArrayList();
		if (resourceIds.size() == 0) {
			return ret;
		}
		// 返回的顺序保持输入的resourceId的顺序
		List<Mission> missions = missionMapper.batchSelect(resourceIds);
		Map<String, Mission> map = Maps.newHashMap();
		for (Mission mission : missions) {
			map.put(mission.getResourceId(), mission);
		}
		for (String resourceId : resourceIds) {
			if (map.containsKey(resourceId)) {
				ret.add(map.get(resourceId));
			}
		}
		return ret;
	}

	@Override
	public List<Mission> batchGetByRoom(String roomId) {
		return missionMapper.selectByRoomId(roomId);
	}

	@Override
	public void release(String missionId, String roleId, String currentUserId) {
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}

		CrRole role = crRoleMapper.selectById(roleId);
		if (role == null) {
			log.error("role does not exist！roleId=[{}]", roleId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		List<CrTask> crTasks = crTaskMapper.selectByMissionId(missionId);

		if (crTasks.size() > 0) {

			throw new BusinessException(CrBusinessErrorCode.MISSION_ISSUED);
		}

		Date releaseTime = new Date();

		List<CrTask> tasks = new ArrayList<>();
		for (String userId : JsonUtil.toJsonObjectArray(role.getUserIds(), String.class)) {
			CrTask task = new CrTask();
			task.setMissionId(mission.getResourceId());
			task.setUserId(userId);
			task.setStatus(CrTask.TaskStatus.RELEASED.getCode());
			task.setReleaseTime(releaseTime);
			task.setRoomId(mission.getRoomId());
			task.setRoleId(role.getResourceId());
			task.setBusinessType(role.getBusinessType());

			tasks.add(task);
		}

		CrTaskLog taskLog = new CrTaskLog();
		taskLog.setRoomId(mission.getRoomId());
		taskLog.setMissionId(mission.getResourceId());
		taskLog.setActionCode(CrTaskLog.ActionCode.RELEASE.getCode());
		taskLog.setUserId(currentUserId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.batchInsert(tasks);
				crTaskLogMapper.insert(taskLog);
			}
		});
	}

	@Override
	public Boolean addDynamic(Mission mission, CrRole role, Integer stage) {
		// 校验是否和角色既存任务重名
		List<MissionStageScheme> missionStageSchemeList = JsonUtil
				.toJsonObjectArray(role.getMissionScheme(), MissionStageScheme.class);
		List<String> missionIds = Lists.newArrayList();
		for (MissionStageScheme scheme : missionStageSchemeList) {
			missionIds.addAll(scheme.getMissions());
		}
		if (checkNameDuplicate(mission.getTitle(), missionIds)) {
			throw new BusinessException(CrBusinessErrorCode.DYNAMIC_MISSION_TITLE_DUPLICATE);
		}
		// 保存
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				save(mission);
				addDynamicMissionInfo(missionStageSchemeList, stage, mission.getResourceId());
				role.setMissionScheme(JSONObject.toJSONString(missionStageSchemeList));
				crRoleMapper.updateById(role);
			}
		});
		return true;
	}

	@Override
	public List<MissionCategory> listCategories() {
		return new ArrayList<>(categoryCache.values());
	}

	@Override
	public Map<String, MissionCompletion> getMissionStatusData(String roomId) {

		List<CrTask> tasks = crTaskMapper.selectByRoomId(roomId);
		Map<String, MissionCompletion> missionMap = Maps.newHashMap();
		for (CrTask task : tasks) {
			if (!missionMap.containsKey(task.getMissionId())) {
				missionMap.put(task.getMissionId(), new MissionCompletion(1, 0));
			}
		}

		for (CrTask task : tasks) {
			if (task.isFinished()) {
				missionMap.get(task.getMissionId()).addCompletionNum();
			}
		}
		return missionMap;
	}

	@Override
	public MissionCategory getCategoryById(String categoryId) {
		return categoryCache.get(categoryId);
	}

	@Override
	public Map<String, String> copyScheme(List<String> missionTemplateIds, String userId,
			String roomId) {
		Map<String, String> missionCopyMap = Maps.newHashMap();
		if (missionTemplateIds.size() > 0) {
			List<MissionTemplate> missionTemplates = missionTemplateMapper
					.selectBatchIds(missionTemplateIds);
			if (missionTemplates.size() > 0) {
				List<Mission> missions = new ArrayList<>();

				for (MissionTemplate missionTemplate : missionTemplates) {
					Mission mission = BeanUtils.copy(missionTemplate, Mission.class);
					mission.setShowWriteup(false);
					mission.setCreateUserId(userId);
					mission.setRoomId(roomId);
					mission.setResourceId(IDGeneratorUtil.getID());

					missions.add(mission);
					missionCopyMap.put(missionTemplate.getResourceId(), mission.getResourceId());
				}

				missionMapper.batchInsert(missions);
			}

		}
		return missionCopyMap;
	}

	@Override
	public void openOrCloseWriteup(String missionId, Boolean showWriteup) {
		Mission mission = missionMapper.selectById(missionId);
		mission.setShowWriteup(showWriteup);
		String userId = UserInfoContext.getUserContext().getUserId();
		CrTaskLog taskLog = CrTaskLog.builder().roomId(mission.getRoomId()).missionId(missionId)
				.userId(userId).build();
		if (showWriteup) {
			taskLog.setActionCode(CrTaskLog.ActionCode.OPEN_WRITEUP.getCode());
		} else {
			taskLog.setActionCode(CrTaskLog.ActionCode.CLOSE_WRITEUP.getCode());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				missionMapper.updateById(mission);
				crTaskLogMapper.insert(taskLog);

			}
		});
	}

	@Override
	public void addSchemeTemplate(MissionSchemeTemplate schemeTemplate) {
		missionSchemeTemplateMapper.insert(schemeTemplate);
	}

	@Override
	public void batchDeleteSchemeTemplate(List<String> schemeTemplateIds) {
		missionSchemeTemplateMapper.deleteBatchIds(schemeTemplateIds);
	}

	@Override
	public MissionSchemeTemplate getSchemeTemplateById(String schemeTemplateId) {
		return missionSchemeTemplateMapper.selectById(schemeTemplateId);
	}

	@Override
	public void updateMissionSchemeTemplateById(MissionSchemeTemplate toUpdateSchemeTemplate) {
		missionSchemeTemplateMapper.updateById(toUpdateSchemeTemplate);
	}

	@Override
	public ListRsp<MissionSchemeTemplateRsp> pageSchemeTemplateRsp(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<MissionSchemeTemplate> iPage = missionSchemeTemplateMapper.list(page,
				pageReqBase.getPattern());
		iPage.convert(this::schemeTemplateToRsp);

		return PageUtil.getListResult(iPage);
	}

	@Override
	public MissionSchemeTemplateRsp getSchemeTemplateRspById(String resourceId) {
		MissionSchemeTemplate template = missionSchemeTemplateMapper.selectById(resourceId);

		return schemeTemplateToRsp(template);
	}

	@Override
	public List<DynamicData> getTaskLogByRoomIdAndUserId(String roomId, String studentId) {
		List<String> taskLogIds = crTaskLogMapper.getReleaseIdsByRoomIdAndUserId(roomId, studentId);

		List<DynamicData> dynamicDataList = Lists.newArrayList();
		if (taskLogIds.size() > 0) {
			List<Integer> actionCodes = Lists.newArrayList();
			actionCodes.add(CrTaskLog.ActionCode.RELEASE.getCode());
			actionCodes.add(CrTaskLog.ActionCode.MAN_SUCCESS.getCode());
			actionCodes.add(CrTaskLog.ActionCode.MAN_FAILURE.getCode());
			actionCodes.add(CrTaskLog.ActionCode.MAN_REJECT.getCode());
			actionCodes.add(CrTaskLog.ActionCode.SUBMIT.getCode());

			dynamicDataList = crTaskLogMapper.getDynamicLogByRoomIdAndUserId(roomId, studentId,
					taskLogIds, actionCodes);
		}

		return dynamicDataList;
	}

	private MissionSchemeTemplateRsp schemeTemplateToRsp(MissionSchemeTemplate schemeTemplate) {
		MissionSchemeTemplateRsp templateRsp = BeanUtils.deepCopy(schemeTemplate,
				MissionSchemeTemplateRsp.class);
		MissionSchemeTemplateConfig config = JsonUtil.toJsonObject(
				schemeTemplate.getTemplateConfig(), MissionSchemeTemplateConfig.class);
		templateRsp.setTemplate(config.getTemplate());
		templateRsp.setColor(config.getColor());
		templateRsp.setConfig(schemeTemplate.getTemplateData());
		templateRsp.setCombatConfig(JsonUtil.toJsonObjectArray(schemeTemplate.getTemplateData(),
				MissionSchemeStage.class));
		templateRsp.setCreateUser(schemeTemplate.getCreateUserId());

		return templateRsp;
	}

	@Override
	public String getTeamTrainingManual(String roomId) {
		RoleQuery roleQuery = new RoleQuery(roomId, CrRole.TeamRoleGroupCode.STUDENT.getCode());
		List<CrRole> roles = crRoleMapper.selectCrRoleByRoomId(roleQuery);
		List<CrRoleMissionSchemeStage> allStages = Lists.newArrayList();
		for (CrRole role : roles) {
			List<CrRoleMissionSchemeStage> stages = JsonUtil
					.toJsonObjectArray(role.getMissionScheme(), CrRoleMissionSchemeStage.class);
			allStages.addAll(stages);
		}

		List<Mission> missions = missionMapper.selectByRoomId(roomId);
		Map<String, Mission> missionMap = Maps.newHashMap();
		for (Mission mission : missions) {
			missionMap.put(mission.getResourceId(), mission);
		}

		TrainingManualTeam manual = new TrainingManualTeam(allStages, missionMap,
				CommonContext.getLanguage());
		return manual.toMarkDown();
	}

	@Override
	public List<MissionTemplate> batchInsertMissionTemplate(List<LoadMission> templateQuestions) {
		String userId = UserInfoContext.getUserContext().getUserId();
		List<MissionTemplate> missionTemplates = templateQuestions.stream().map(loadMission -> {
			MissionTemplate missionTemplate = BeanUtils.copy(loadMission, MissionTemplate.class);
			missionTemplate.setCreateUserId(userId);
			missionTemplate.setCategoryId(loadMission.getCategory());
			missionTemplate.setResourceId(IDGeneratorUtil.getID());

			return missionTemplate;
		}).collect(Collectors.toList());

		missionMapper.batchInsertMissionTemplate(missionTemplates);
		return missionTemplates;
	}

	@Override
	public List<MissionTemplate> batchGetByMissionSchemeJson(String schemeJson) {
		String reg = String.format("\"([a-z0-9]{%d})\"", CrCommonConstants.GENERATED_UUID_LENGTH);
		List<String> missionTemplateIds = LocalStringUtil.getMatchStr(schemeJson, reg, 1);
		if (missionTemplateIds.size() == 0) {
			return new ArrayList<>();
		}
		return missionTemplateMapper.selectBatchIds(missionTemplateIds);
	}

	@Override
	public Boolean batchLoadMissionTemplates(List<MissionTemplate> missionTemplates) {
		if (missionTemplates == null || missionTemplates.size() == 0) {
			return false;
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		for (MissionTemplate template : missionTemplates) {
			template.setId(null);
			template.setCreateTime(null);
			template.setUpdateTime(null);
			template.setCreateUserId(userId);

			if (missionTemplateMapper.selectById(template.getResourceId()) != null) {
				missionTemplateMapper.updateById(template);
			} else {
				missionTemplateMapper.insert(template);
			}
		}
		return true;
	}

	private void addDynamicMissionInfo(List<MissionStageScheme> missionStageSchemeList,
			Integer stage, String missionId) {

		if (stage == null) {
			stage = 0;
		}

		if (missionStageSchemeList.size() > 0) {
			int index = 0;

			for (MissionStageScheme missionStageScheme : missionStageSchemeList) {
				List<String> missions = missionStageScheme.getMissions();

				if (index++ == stage) {
					missions.add(missionId);
					missionStageScheme.setDynamic(getDynamicIds(missionStageScheme, missionId));
					missionStageScheme.setMissions(missions);
				}

			}
		} else {
			List<String> missions = Lists.newArrayList();
			missions.add(missionId);

			MissionStageScheme missionStageScheme = new MissionStageScheme();

			missionStageScheme.setStage(stage.toString());
			missionStageScheme.setDynamic(getDynamicIds(missionStageScheme, missionId));
			missionStageScheme.setMissions(missions);

			missionStageSchemeList.add(missionStageScheme);
		}
	}

	private List<String> getDynamicIds(MissionStageScheme missionStageScheme, String resourceId) {

		List<String> ids = missionStageScheme.getDynamic();
		if (ids == null) {
			ids = Lists.newArrayList();
		}

		ids.add(resourceId);

		return ids;
	}

	/**
	 * 校验动态任务是否与角色既存任务重名
	 * @param title
	 * @param missionIds
	 * @return true ：重名
	 */
	private Boolean checkNameDuplicate(String title, List<String> missionIds) {
		if (missionIds.isEmpty()) {
			return false;
		}
		return missionMapper.selectByTitleAndIds(title, missionIds) != null;
	}

	@Override
	public void validateMission(String missionsScheme) {
		ColorMission<AddTemplateMission> colorMission = JSON.parseObject(missionsScheme,
				new TypeReference<ColorMission<AddTemplateMission>>() {});

		validateMission(colorMission);
	}

	@Override
	public void getMissionContent(MissionSchemeObject<AddTemplateMission> schemeData) {
		List<String> missionIds = StreamApiUtils.map(schemeData.getMissions(),
				AddTemplateMission::getResourceId);

		List<MissionTemplate> missions = missionTemplateMapper.selectBatchIds(missionIds);

		Map<String, MissionTemplate> missionMap = StreamApiUtils.toMap(missions,
				MissionTemplate::getResourceId, pc -> pc);

		for (AddTemplateMission mission : schemeData.getMissions()) {

			mission.setTitle(missionMap.getOrDefault(mission.getResourceId(), new MissionTemplate())
					.getTitle());
			mission.setDescription(missionMap
					.getOrDefault(mission.getResourceId(), new MissionTemplate()).getDescription());

		}

	}

	@Override
	public void validateMission(ColorMission<AddTemplateMission> colorMission) {
		if (null == colorMission || null == colorMission.getSchemeData()) {
			return;
		}

		List<MissionSchemeObject<AddTemplateMission>> schemeData = colorMission.getSchemeData();
		for (MissionSchemeObject<AddTemplateMission> schemeDatum : schemeData) {

			if (schemeDatum.getMissions().size() == 0) {
				throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_BE_EMPTY);
			}

			List<String> missionIds = StreamApiUtils.map(schemeDatum.getMissions(),
					AddTemplateMission::getResourceId);
			List<MissionTemplate> missionTemplates = missionTemplateMapper
					.selectBatchIds(missionIds);

			if (missionTemplates.size() < missionIds.size()) {
				throw new BusinessException(CrBusinessErrorCode.MISSION_LESS);
			}

		}
	}

	@Override
	public void validateRBMission(JSONObject jsonObject) {
		JSONObject missionsScheme = jsonObject.getJSONObject("missions_scheme");

		missionsSchemeValidator(missionsScheme);

	}

	@Override
	public void missionsSchemeValidator(JSONObject missionsScheme) {
		List<String> trainMissions = org.apache.commons.compress.utils.Lists.newArrayList();
		missionsScheme.getJSONArray("scheme_data").toJavaList(JSONObject.class).forEach(stage -> {
			stage.getJSONArray("missions").toJavaList(JSONObject.class).forEach(mission -> {
				String missionId = mission.getString("resource_id");
				if (missionId == null || missionId.isEmpty()) {
					throw new BusinessException(CrBusinessErrorCode.EMPTY_MISSION_TEMPLATE);
				}
				trainMissions.add(missionId);
			});
		});
		if (trainMissions.size() > 0 && trainMissions.size() != missionTemplateMapper
				.selectBatchIds(trainMissions).size()) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_TEMPLATE_INVALID);
		}
	}
}
