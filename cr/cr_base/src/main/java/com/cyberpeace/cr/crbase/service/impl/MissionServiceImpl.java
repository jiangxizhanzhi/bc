package com.cyberpeace.cr.crbase.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.mission.MissionTemplatePageReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionSchemeTemplateRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.CrBadgeService;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.*;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务Service实现
 *
 * @author yinqingrong
 * @date 2024/11/07
 */
@Service
@Slf4j
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {

	/**
	 * 任务Mapper
	 */
	@Autowired
	private MissionMapper missionMapper;

	/**
	 * 任务模板Mapper
	 */
	@Autowired
	private MissionTemplateMapper missionTemplateMapper;

	/**
	 * 靶场任务Mapper
	 */
	@Autowired
	private CrTaskMapper crTaskMapper;

	/**
	 * 靶场角色Mapper
	 */
	@Autowired
	private CrRoleMapper crRoleMapper;

	/**
	 * 靶场任务日志Mapper
	 */
	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	/**
	 * 靶场常数Service
	 */
	@Autowired
	private CrConstantsService crConstantsService;

	/**
	 * 任务方案模板Mapper
	 */
	@Autowired
	private MissionSchemeTemplateMapper missionSchemeTemplateMapper;

	/**
	 * 雷达标签Mapper
	 */
	@Autowired
	private RadarLabelMapper radarLabelMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 靶场徽章Service
	 */
	@Autowired
	private CrBadgeService crBadgeService;

	/**
	 * 类别缓存
	 */
	private volatile Map<String, MissionCategoryCache> categoryCache;

	/**
	 * 锁
	 */
	private Object lock = new Object();

	/**
	 * 新增任务模板
	 *
	 * @param missionTemplate 任务模板
	 * @return {@code MissionTemplate }
	 */
	@Override
	public MissionTemplate addMissionTemplate(MissionTemplate missionTemplate) {
		missionTemplateMapper.insert(missionTemplate);
		return missionTemplate;
	}

	/**
	 * 获取由任务模板id
	 *
	 * @param missionTemplateId 任务模板id
	 * @return {@code MissionTemplate }
	 */
	@Override
	public MissionTemplate getByMissionTemplateId(String missionTemplateId) {
		return missionTemplateMapper.selectById(missionTemplateId);
	}

	/**
	 * 更新任务模板
	 *
	 * @param missionTemplate 任务模板
	 */
	@Override
	public void updateMissionTemplate(MissionTemplate missionTemplate) {
		missionTemplateMapper.updateById(missionTemplate);
	}

	/**
	 * 获取由id
	 *
	 * @param missionId 任务id
	 * @return {@code Mission }
	 */
	@Override
	public Mission getById(String missionId) {
		return missionMapper.selectById(missionId);
	}

	/**
	 * 批量获取
	 *
	 * @param resourceIds 资源id列表
	 * @return {@code List<Mission> }
	 */
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

	/**
	 * 批量获取由房间
	 *
	 * @param roomId 房间id
	 * @return {@code List<Mission> }
	 */
	@Override
	public List<Mission> batchGetByRoom(String roomId) {
		return missionMapper.selectByRoomId(roomId);
	}

	/**
	 * 释放
	 *
	 * @param missionId     任务id
	 * @param roleId        角色id
	 * @param currentUserId 当前用户id
	 */
	@Override
	public void release(String missionId, String roleId, String currentUserId) {
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			log.error("mission does not exist！missionId=[{}]", missionId);
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}

		CrRole role = crRoleMapper.selectById(roleId);
		if (role == null) {
			log.error("role does not exist！roleId=[{}]", roleId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		List<CrTask> crTasks = crTaskMapper.selectByMissionId(missionId);

		if (crTasks.size() > 0) {
			log.error("Mission released! missionId = [{}]",missionId);
			throw new BusinessException(CrBusinessErrorCode.MISSION_ISSUED);
		}

		Date releaseTime = new Date();

		List<CrTask> tasks = new ArrayList<>();
		for (String userId : role.getUserIdList()) {
			CrTask task = new CrTask();
			task.setMissionId(mission.getResourceId());
			task.setUserId(userId);
			task.setStatus(CrTask.TaskStatus.RELEASED.getCode());
			task.setReleaseTime(releaseTime);
			task.setRoomId(mission.getRoomId());
			task.setRoleId(role.getResourceId());
			task.setBusinessType(role.getBusinessType());
			task.setMissionTemplateId(mission.getMissionTemplateId());

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

	/**
	 * 角色释放
	 *
	 * @param roomId        房间id
	 * @param roleId        角色id
	 * @param currentUserId 当前用户id
	 */
	@Override
	public void roleRelease(String roomId, String roleId, String currentUserId) {
		List<Mission> missions = lambdaQuery().eq(Mission::getRoomId, roomId).list();

		CrRole role = crRoleMapper.selectById(roleId);
		if (role == null) {
			log.error("role does not exist！roleId=[{}]", roleId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		List<CrTask> pendingReleaseTasks = new ArrayList<>();

		missions.forEach(mission -> {
			CrTask task = new CrTask();

			task.setMissionId(mission.getResourceId());
			task.setMissionTemplateId(mission.getMissionTemplateId());
			task.setUserId(currentUserId);
			task.setStatus(CrTask.TaskStatus.RELEASED.getCode());
			task.setReleaseTime(new Date());
			task.setRoomId(mission.getRoomId());
			task.setRoleId(role.getResourceId());
			task.setBusinessType(role.getBusinessType());

			pendingReleaseTasks.add(task);
		});

		if (!pendingReleaseTasks.isEmpty()) {
			crTaskMapper.batchInsert(pendingReleaseTasks);
		}
	}

	/**
	 * 新增动态
	 *
	 * @param mission        任务
	 * @param role           角色
	 * @param stage          阶段
	 * @param afterMissionId 后任务id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean addDynamic(Mission mission, CrRole role, Integer stage,String afterMissionId) {
		// 校验是否和角色既存任务重名
		List<MissionStageScheme> missionStageSchemeList = role.getMissionStageSchemes();
		List<String> missionIds = Lists.newArrayList();
		for (MissionStageScheme scheme : missionStageSchemeList) {
			missionIds.addAll(scheme.getMissions());
		}
		if (checkNameDuplicate(mission.getTitle(), missionIds)) {
			log.error("Mission name duplicates with existed missions! title = [{}]", mission.getTitle());
			throw new BusinessException(CrBusinessErrorCode.DYNAMIC_MISSION_TITLE_DUPLICATE);
		}
		// 保存
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				save(mission);
				addDynamicMissionInfo(missionStageSchemeList, stage, mission.getResourceId(),afterMissionId);
				role.setMissionStageSchemes(missionStageSchemeList);
				crRoleMapper.updateById(role);
			}
		});
		return true;
	}

	/**
	 * 列表类别
	 *
	 * @return {@code List<MissionCategory> }
	 */
	@Override
	public List<MissionCategory> listCategories() {
		// read categories
		ensureCache();
		List<MissionCategoryCache> missionCategories = Lists.newArrayList(categoryCache.values());
		// return
		return StreamApiUtils.map(missionCategories,
				category -> new MissionCategory(category.getResourceId(),
						category.selectI18nName()));
	}

	/**
	 * 获取任务状态数据
	 *
	 * @param roomId 房间id
	 * @return {@code Map<String, MissionCompletion> }
	 */
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

	/**
	 * 获取类别由id
	 *
	 * @param categoryId 类别id
	 * @return {@code MissionCategory }
	 */
	@Override
	public MissionCategory getCategoryById(String categoryId) {
		ensureCache();
		MissionCategoryCache category = categoryCache.get(categoryId);
		return new MissionCategory(categoryId, category.selectI18nName());
	}

	/**
	 * 复制方案
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @param userId             用户id
	 * @param roomId             房间id
	 * @return {@code Map<String, String> }
	 */
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
					mission.setMissionTemplateId(missionTemplate.getResourceId());
					mission.setResourceId(IDGeneratorUtil.getID());

					missions.add(mission);
					missionCopyMap.put(missionTemplate.getResourceId(), mission.getResourceId());
				}

				missionMapper.batchInsert(missions);
			}

		}
		return missionCopyMap;
	}

	/**
	 * 开启或关闭撰写
	 *
	 * @param missionId   任务id
	 * @param showWriteup 显示 Writeup
	 */
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

	/**
	 * 新增方案模板
	 *
	 * @param schemeTemplate 方案模板
	 */
	@Override
	public void addSchemeTemplate(MissionSchemeTemplate schemeTemplate) {
		missionSchemeTemplateMapper.insert(schemeTemplate);
	}

	/**
	 * 批量删除方案模板
	 *
	 * @param schemeTemplateIds 方案模板id列表
	 */
	@Override
	public void batchDeleteSchemeTemplate(List<String> schemeTemplateIds) {
		missionSchemeTemplateMapper.deleteBatchIds(schemeTemplateIds);
	}

	/**
	 * 获取方案模板由id
	 *
	 * @param schemeTemplateId 方案模板id
	 * @return {@code MissionSchemeTemplate }
	 */
	@Override
	public MissionSchemeTemplate getSchemeTemplateById(String schemeTemplateId) {
		return missionSchemeTemplateMapper.selectById(schemeTemplateId);
	}

	/**
	 * 更新任务方案模板由id
	 *
	 * @param toUpdateSchemeTemplate 更新 Scheme 模板
	 */
	@Override
	public void updateMissionSchemeTemplateById(MissionSchemeTemplate toUpdateSchemeTemplate) {
		missionSchemeTemplateMapper.updateById(toUpdateSchemeTemplate);
	}

	/**
	 * 分页方案模板响应
	 *
	 * @param pageReqBase 分页请求体基础
	 * @return {@code ListRsp<MissionSchemeTemplateRsp> }
	 */
	@Override
	public ListRsp<MissionSchemeTemplateRsp> pageSchemeTemplateRsp(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<MissionSchemeTemplate> iPage = missionSchemeTemplateMapper.list(page,
				pageReqBase.getPattern());
		iPage.convert(this::schemeTemplateToRsp);

		return PageUtil.getListResult(iPage);
	}

	/**
	 * 获取方案模板响应由id
	 *
	 * @param resourceId 资源id
	 * @return {@code MissionSchemeTemplateRsp }
	 */
	@Override
	public MissionSchemeTemplateRsp getSchemeTemplateRspById(String resourceId) {
		MissionSchemeTemplate template = missionSchemeTemplateMapper.selectById(resourceId);

		return schemeTemplateToRsp(template);
	}

	/**
	 * 获取任务日志由房间id和用户id
	 *
	 * @param roomId    房间id
	 * @param studentId 学员id
	 * @return {@code List<DynamicData> }
	 */
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

	/**
	 * 方案模板自响应
	 *
	 * @param schemeTemplate 方案模板
	 * @return {@code MissionSchemeTemplateRsp }
	 */
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

	/**
	 * 获取团队训练手动
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 */
	@Override
	public String getTeamTrainingManual(String roomId) {
		List<CrRole> roles = crRoleMapper
				.selectCrRoleByRoomId(new RoleQuery(roomId, CrRole.TeamRoleGroup.STUDENT));
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

		TrainingManualTeam manual = new TrainingManualTeam(allStages, missionMap);
		return manual.toMarkDown();
	}

	/**
	 * 批量加载任务模板
	 *
	 * @param missionTemplates 任务模板
	 * @return {@code Boolean }
	 */
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

	/**
	 * 新增动态任务信息
	 *
	 * @param missionStageSchemeList 任务阶段方案列表
	 * @param stage                  阶段
	 * @param missionId              任务id
	 * @param afterMissionId         后任务id
	 */
	private void addDynamicMissionInfo(List<MissionStageScheme> missionStageSchemeList,
			Integer stage, String missionId,String afterMissionId) {

		if (stage == null) {
			stage = 0;
		}

		if (missionStageSchemeList.size() > 0) {
			int index = 0;

			for (MissionStageScheme missionStageScheme : missionStageSchemeList) {
				List<String> missions = missionStageScheme.getMissions();

				if (index++ == stage) {
					int i = missions.indexOf(afterMissionId);
					if ("first".equals(afterMissionId)) {
						missions.add(0,missionId);
					} else if (StringUtils.isNotBlank(afterMissionId)&&i!=-1) {
						missions.add(i+1,missionId);
					}else {
						missions.add(missionId);
					}
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

	/**
	 * 获取动态id列表
	 *
	 * @param missionStageScheme 任务阶段计划
	 * @param resourceId         资源id
	 * @return {@code List<String> }
	 */
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
	 *
	 * @param title
	 * @param missionIds
	 * @return true ：重名
	 */
	private Boolean checkNameDuplicate(String title, List<String> missionIds) {
		if (missionIds.isEmpty()) {
			return false;
		}
		return missionMapper.existByTitleAndIds(title, missionIds);
	}

	/**
	 * 获取任务内容
	 *
	 * @param schemeData 方案数据
	 */
	@Override
	public void getMissionContent(MissionSchemeObject<AddTemplateMission> schemeData) {
		List<String> missionIds = StreamApiUtils.map(schemeData.getMissions(),
				AddTemplateMission::getResourceId);

		if (missionIds.size() > 0) {
			List<MissionTemplate> missions = missionTemplateMapper.selectBatchIds(missionIds);

			Map<String, MissionTemplate> missionMap = StreamApiUtils.toMap(missions,
					MissionTemplate::getResourceId, pc -> pc);

			for (AddTemplateMission mission : schemeData.getMissions()) {

				mission.setTitle(missionMap
						.getOrDefault(mission.getResourceId(), new MissionTemplate()).getTitle());
				mission.setDescription(
						missionMap.getOrDefault(mission.getResourceId(), new MissionTemplate())
								.getDescription());

			}

		}

	}

	/**
	 * 验证任务
	 *
	 * @param colorMission 色彩使命
	 */
	@Override
	public void validateMission(ColorMission<AddTemplateMission> colorMission) {
		SerializeConfig config = new SerializeConfig();
		config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
		String json = JSON.toJSONString(colorMission, config);

		missionsSchemeValidator(JSON.parseObject(json));
	}

	/**
	 * Missions Scheme 验证者
	 *
	 * @param missionsScheme 任务计划
	 */
	@Override
	public void missionsSchemeValidator(JSONObject missionsScheme) {

		missionsScheme.getJSONArray("scheme_data").toJavaList(JSONObject.class).forEach(stage -> {

			List<String> trainMissions = Lists.newArrayList();

			stage.getJSONArray("missions").toJavaList(JSONObject.class).forEach(mission -> {
				String missionId = mission.getString("resource_id");
				if (missionId == null || missionId.isEmpty()) {
					log.error("Mission Template is empty! missionId is empty");
					throw new BusinessException(CrBusinessErrorCode.EMPTY_MISSION_TEMPLATE);
				}
				trainMissions.add(missionId);
			});

			if (trainMissions.size() > 0) {
				List<MissionTemplate> missions = missionTemplateMapper
						.selectBatchIds(trainMissions);

				List<String> titles = Lists.newArrayList();
				for (MissionTemplate template : missions) {
					titles.add(template.getTitle());
					// 校验任务名称长度
					CrCommonValidator.validateNameLen(template.getTitle(),
							CrBusinessErrorCode.INVALID_MISSION_NAME);
				}
				// 校验阶段内任务重名
				CrCommonValidator.checkStringListDuplicate(titles,
						CrBusinessErrorCode.DUPLICATED_MISSION_NAME);

				if (trainMissions.size() != missions.size()) {
					log.error("Mission id duplicate  or part of missions miss! trainMissions = [{}], missions = [{}]",
							StringUtils.join(trainMissions, ","), JsonUtil.toJsonString(missions));
					throw new BusinessException(CrBusinessErrorCode.MISSION_LESS);
				}
			}
		});

	}

	/**
	 * 制作转储文件
	 *
	 * @param templateIds 模板id列表
	 * @param tempDir     临时目录
	 * @return {@code String }
	 * @throws IOException io异常
	 */
	@Override
	public String makeDumpFile(List<String> templateIds, String tempDir) throws IOException {
		if (templateIds.isEmpty()) {
			log.error("Resource id list is empty!");
			throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
		}
		// 1. make temp dir
		FileUtil.mkdirs(tempDir);
		String data = dumpSchemeTemplates(templateIds);
		// 4. generate data.json
		String jsonPath = tempDir + File.separator + CrCommonConstants.RESOURCE_FILE_NAME;
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonPath),
				StandardCharsets.UTF_8);
		FileUtil.writeString(data, osw);
		// 5. generate zip file, return zip file path
		return ZipUtil.toZip(tempDir);
	}

	/**
	 * 批量加载方案
	 *
	 * @param unZipPath un zip 路径
	 */
	@Override
	public void batchLoadScheme(String unZipPath) {
		// 1. read data.json
		String dataJson = FileUtil.getContent(
				new File(unZipPath + File.separator + CrCommonConstants.RESOURCE_FILE_NAME));
		List<MissionSchemeTemplate> missionSchemeTemplates = JsonUtil.toJsonObjectArray(dataJson,
				MissionSchemeTemplate.class);
		String userId = UserInfoContext.getUserContext().getUserId();
		for (MissionSchemeTemplate template : missionSchemeTemplates) {
			// validate
			if (template.getDescription() == null || template.getTitle() == null
					|| template.getTemplateData() == null || template.getTemplateConfig() == null) {
				log.error("Resource zip invalid! template = [{}]", JsonUtil.toJsonString(template));
				throw new BusinessException(CrBusinessErrorCode.INVALID_RESOURCE_ZIP);
			}
			template.setId(null);
			template.setCreateUserId(userId);
			template.setCreateTime(null);
			template.setUpdateTime(null);

			// 按5.3逻辑处理重名：重名跳过
			template.setResourceId(IDGeneratorUtil.getID());
			if (!missionSchemeTemplateMapper.existByTitle(template.getTitle())) {
				missionSchemeTemplateMapper.insert(template);
			}
		}
	}

	/**
	 * 获取雷达标签
	 *
	 * @return {@code List<RadarLabel> }
	 */
	@Override
	public List<RadarLabel> getRadarLabel() {
		return radarLabelMapper.selectList();
	}

	/**
	 * 更新 Radar 标签
	 *
	 * @param radarLabelList 雷达标签列表
	 */
	@Override
	public void updateRadarLabel(List<RadarLabel> radarLabelList) {
		for (RadarLabel radarLabel : radarLabelList) {
			radarLabelMapper.updateById(radarLabel);
		}

	}

	/**
	 * 检查 Scheme 模板标题重复
	 *
	 * @param title 标题
	 */
	@Override
	public void checkSchemeTemplateTitleDuplicate(String title) {
		if (missionSchemeTemplateMapper.existByTitle(title)) {
			log.error("Mission solution name duplicate! title = [{}]", title);
			throw new BusinessException(
					CrBusinessErrorCode.DUPLICATED_MISSION_SCHEME_TEMPLATE_TITLE);
		}
	}

	/**
	 * 获取任务id列表由方案
	 *
	 * @param missionStageSchemes 任务阶段计划
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getMissionIdsByScheme(List<MissionStageScheme> missionStageSchemes) {
		List<String> missionIds = Lists.newArrayList();
		missionStageSchemes.forEach(stage -> missionIds.addAll(stage.getMissions()));
		return missionIds;
	}

	/**
	 * 分页任务模板
	 *
	 * @param pageReq 分页请求体
	 * @return {@code ListRsp<MissionTemplateRes> }
	 */
	@Override
	public ListRsp<MissionTemplateRes> pageMissionTemplate(MissionTemplatePageReq pageReq) {
		Page page = pageReq.toPageParam();
		IPage<MissionTemplateRes> iPage = missionTemplateMapper.pageWithBusiness(page,
				pageReq.getType(), pageReq.getDifficulty(), pageReq.getPattern());
		return PageUtil.getListResult(iPage);
	}

	/**
	 * 批量获取由房间和靶场角色
	 *
	 * @param roomId     房间id
	 * @param crRoleList 靶场角色列表
	 * @return {@code List<Mission> }
	 */
	@Override
	public List<Mission> batchGetByRoomAndCrRoles(String roomId, List<CrRole> crRoleList) {
		List<String> missionIds = Lists.newArrayList();

		for (CrRole crRole : crRoleList) {
			missionIds.addAll(getMissionIdsByScheme(crRole.getMissionStageSchemes()));
		}

		return batchGet(missionIds);
	}

	/**
	 * 更新任务验证
	 *
	 * @param missionId 任务id
	 * @param roomId    房间id
	 * @param title     标题
	 */
	@Override
	public void updateMissionValidate(String missionId, String roomId, String title) {
		CrRole role = crRoleMapper.selectByRoomIdAndMissionId(roomId, missionId);
		if (role == null) {
			log.error("Role not exist!, roomId = [{}], missionId = [{}]", roomId, missionId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		// 校验是否和角色既存任务重名
		List<MissionStageScheme> missionStageSchemeList = role.getMissionStageSchemes();
		List<String> missionIds = Lists.newArrayList();
		for (MissionStageScheme stage : missionStageSchemeList) {
			missionIds = stage.getMissions();
			if (missionIds.contains(missionId)) {
				missionIds.remove(missionId);
				break;
			}
		}
		if (checkNameDuplicate(title, missionIds)) {
			log.error("Duplicated mission names in one stage! title = [{}], missionIds = [{}]", title, StringUtils.join(missionIds));
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_MISSION_NAME);
		}
	}

	/**
	 * 批量释放
	 *
	 * @param missionIds    任务id列表
	 * @param roleId        角色id
	 * @param currentUserId 当前用户id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean batchRelease(List<String> missionIds, String roleId, String currentUserId) {
		List<Mission> missions = missionMapper.selectBatchIds(missionIds);
		if (CollectionUtils.isEmpty(missions)) {
			log.error("missions does not exist！missionIds=[{}]", JsonUtil.arrayToString(missionIds));
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}

		CrRole role = crRoleMapper.selectById(roleId);
		if (role == null) {
			log.error("role does not exist！roleId=[{}]", roleId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		//已下发的任务
		List<CrTask> crTasks = crTaskMapper.selectByMissionIds(missionIds);

		//过滤已下发的任务
		if (crTasks.size() > 0) {
			Set<String> existMissionIds = crTasks.stream().map(a -> a.getMissionId()).collect(Collectors.toSet());
			missions=missions.stream().filter(a->!existMissionIds.contains(a.getResourceId())).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(missions)) {
				return false;
			}
		}

		Date releaseTime = new Date();

		List<CrTask> tasks = new ArrayList<>();
		List<CrTaskLog> taskLogs = new ArrayList<>();
		//根据任务和user列表创建任务
		for (Mission mission : missions) {
			for (String userId : role.getUserIdList()) {
				CrTask task = new CrTask();
				task.setMissionId(mission.getResourceId());
				task.setUserId(userId);
				task.setStatus(CrTask.TaskStatus.RELEASED.getCode());
				task.setReleaseTime(releaseTime);
				task.setRoomId(mission.getRoomId());
				task.setRoleId(role.getResourceId());
				task.setBusinessType(role.getBusinessType());
				task.setMissionTemplateId(mission.getMissionTemplateId());

				tasks.add(task);
			}
			CrTaskLog taskLog = new CrTaskLog();
			taskLog.setRoomId(mission.getRoomId());
			taskLog.setMissionId(mission.getResourceId());
			taskLog.setActionCode(CrTaskLog.ActionCode.RELEASE.getCode());
			taskLog.setUserId(currentUserId);
			taskLogs.add(taskLog);
		}


		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.batchInsert(tasks);
				crTaskLogMapper.batchInsert(taskLogs);
				crBadgeService.createBatch(role.getRoomId(),
						CrBadge.BadgeType.MISSION_RELEASE.getCode(), role.getUserIdList());
			}
		});
		return true;
	}

	/**
	 * 获取详情由id
	 *
	 * @param resourceId 资源id
	 * @return {@code MissionDetail }
	 */
	@Override
	public MissionDetail getDetailById(String resourceId) {
		Mission mission = getById(resourceId);
		MissionDetail detail = new MissionDetail();
		detail.setResourceId(mission.getResourceId());
		detail.setCategoryId(mission.getCategoryId());
		detail.setDescription(mission.getDescription());
		detail.setTitle(mission.getTitle());
		detail.setOption(JsonUtil.toJsonObjectArray(mission.getOption(), MissionOption.class));
		detail.setType(mission.getType());
		detail.setAnswer(mission.getAnswer());
		detail.setWriteUp(mission.getWriteUp());
		detail.setShowWriteup(mission.getShowWriteup());
		detail.setSkillPoints(JsonUtil.toJsonObjectArray(mission.getSkillPoints(), Integer.class));
		detail.setScore(mission.getScore());
		return detail;
	}

	/**
	 * 获取任务地图由id列表
	 *
	 * @param missionIds 任务id列表
	 * @return {@code Map<String, Mission> }
	 */
	@Override
	public Map<String, Mission> getMissionMapByIds(List<String> missionIds) {
		if (CollectionUtils.isEmpty(missionIds)) {
			return Maps.newHashMap();
		}
		List<Mission> missions = missionMapper.selectBatchIds(missionIds);
		return StreamApiUtils.toMap(missions, Mission::getResourceId, mission -> mission);
	}

	/**
	 * 转储方案模板
	 *
	 * @param templateIds 模板id列表
	 * @return {@code String }
	 */
	private String dumpSchemeTemplates(List<String> templateIds) {
		List<MissionSchemeTemplate> templateList = missionSchemeTemplateMapper
				.selectBatchIds(templateIds);
		return JsonUtil.arrayToString(templateList);
	}

	/**
	 * do init （执行初始化）
	 */
	private void doInit() {
		List<CrConstantsConfig> missionCategories = crConstantsService
				.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		List<MissionCategoryCache> categories = BeanUtils.deepCopyList(missionCategories,
				MissionCategoryCache.class);

		categoryCache = Maps.newHashMap();

		for (MissionCategoryCache item : categories) {
			categoryCache.put(item.getResourceId(), item);
		}
	}

	/**
	 * 确保缓存
	 */
	private void ensureCache() {
		if (null == categoryCache) {
			synchronized (lock) {
				if (null == categoryCache) {
					doInit();
				}
			}
		}
	}
}
