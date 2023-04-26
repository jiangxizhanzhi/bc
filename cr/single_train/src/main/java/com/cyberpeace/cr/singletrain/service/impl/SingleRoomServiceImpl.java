package com.cyberpeace.cr.singletrain.service.impl;

import static com.cyberpeace.cr.crbase.constant.CrCommonConstants.MP_SCENE_ID_KEY;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.cr.crbase.constant.TrainI18n;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoomList;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTrainStats;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class SingleRoomServiceImpl extends ServiceImpl<SingleRoomMapper, SingleRoom> implements SingleRoomService {

	@Autowired
	private SingleTemplateMapper singleTemplateMapper;

	@Autowired
	private SingleRoomMapper singleRoomMapper;

	@Autowired
	private SingleTemplateService singleTemplateService;

	@Autowired
	private TrainRecordService trainRecordService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private MissionService missionService;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private SceneFilterService sceneFilterService;

	@Autowired
	private CrRoomDeviceApplyService crRoomDeviceApplyService;

	/**
	 * 创建单兵训练
	 *
	 * @param businessTemplateId 训练模板id
	 * @return 单兵训练房间信息
	 */
	@Override
	public SingleRoomBo createRoom(String businessTemplateId) {
		// 已存在运行中训练, 返回
		SingleRoomBo singleRoomBo = getRoomInfo(businessTemplateId);
		if (singleRoomBo.getRoomInfo() != null) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
		}

		BusinessTemplate businessTemplate = businessTemplateService.getById(businessTemplateId);

		// setup single room data
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = new SingleRoom();
		room.setBusinessTemplateId(businessTemplateId);
		room.setStartTime(new Date());
		room.setUserId(userId);
		room.setTrainingRecord("{}");
		room.setIsFinish(false);

		// setup single role
		CrRole singleRole = new CrRole(CrRole.SINGLE_ROLE_GROUP_NAME,
				CrRole.SINGLE_ROLE_GROUP_CODE);
		singleRole.setBusinessType(BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
		singleRole.setUserIdList(Lists.newArrayList(userId));

		// 开启场景
		LocalScene localScene = createLocalScene(businessTemplate);
		String sceneId = localSceneService.startScene(localScene);
		localScene.setMpSceneId(sceneId);
		try {
			// 单兵训练
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					localSceneService.save(localScene);
					room.setLocalSceneId(localScene.getResourceId());
					save(room);
					singleRole.setRoomId(room.getResourceId());
					singleRole.setMissionScheme(crRoleService.getNewRoomScheme(
							JsonUtil.toJsonString(singleRoomBo.getMissionsScheme()),
							room.getResourceId()));
					crRoleService.save(singleRole);
					missionService.roleRelease(room.getResourceId(), singleRole.getResourceId(),
							userId);

					trainRecordService.saveRecord(businessTemplate.getName(), businessTemplateId,
							room.getResourceId(), userId,
							BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
				}
			});
		} catch (Exception e) {
			log.error("db error when start train single scene: ", e);
			localSceneService.deleteRemoteScene(localScene);
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}

		return getRoomInfo(room.getBusinessTemplateId());
	}

	private LocalScene createLocalScene(BusinessTemplate template) {
		LocalScene localScene = new LocalScene();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 用于消息接收，ws推送
		String businessId = template.getResourceId() + '_' + userId;

		localScene.setTopoId(template.getTopoId());
		localScene.setBusinessCode(LocalScene.BusinessCode.SINGLE_TRAINING.getCode());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setBusinessInstanceId(businessId);
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setStartTime(new Date());
		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.SINGLE.getCode());
		localScene.setSceneName(template.getName());
		// 用于前端拼接url的appResourceId
		localScene.setMeta(JSON.toJSONString(
				LocalSceneMeta.builder().appResourceId(template.getResourceId()).build()));

		return localScene;
	}

	/**
	 * 获取单兵训练房间信息
	 *
	 * @param businessTemplateId 训练业务模板id
	 * @return SingleRoomBo
	 */
	@Override
	public SingleRoomBo getRoomInfo(String businessTemplateId) {
		// 获取当前训练模板数据
		SingleTemplateBo singleTemplateBo = singleTemplateService.get(businessTemplateId);
		SingleRoomBo singleRoomBo = new SingleRoomBo();
		BeanUtils.copyProperties(singleTemplateBo, singleRoomBo);

		// 当前用户是否创建房间, 如果为null直接返回
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, userId);

		SingleTrainListReq req = new SingleTrainListReq();
		req.setPattern(singleRoomBo.getEventData().getName());
		req.setPerPage(1000);
		ListRsp<SingleRoomListBo> singleTrainPageList = getSingleTrainPageList(req);
		if (!singleTrainPageList.getList().isEmpty()) {
			for (SingleRoomListBo singleRoomListBo: singleTrainPageList.getList()) {
				if (singleRoomListBo.getResourceId().equals(singleRoomBo.getResourceId())) {
					singleRoomBo.setLastSevenDays(singleRoomListBo.getLastSevenDays());
					singleRoomBo.setTemplateUseCount(singleRoomListBo.getTemplateUseCount());
					break;
				}
			}
		}

		if (singleRoom == null) {
			singleRoomBo.setShowSubmitBtn(false);
			return singleRoomBo;
		} else {
			singleRoomBo.setShowSubmitBtn(!singleRoom.getIsFinish());
		}

		// 获取房间任务方案
		SerializeConfig serializeConfig = new SerializeConfig();
		serializeConfig.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
		JSONObject missionsRecord = JsonUtil.toJsonObject(singleRoom.getTrainingRecord(),
				JSONObject.class);
		// 设置任务完成状态
		List<MissionSchemeObject<MissionContext>> schemeData = crRoleService
				.getCrRoleMissionByRoomIdAndUserId(singleRoom.getResourceId(), userId);
		schemeData.forEach(missionStage -> missionStage.getMissions().forEach(mission -> mission
				.setFinished(missionsRecord.getString(mission.getResourceId()) != null)));
		singleTemplateBo.getMissionsScheme().put("scheme_data",
				JSONArray.toJSON(schemeData, serializeConfig));

		// 生成数据
		SingleRoomInfoBo singleRoomInfoBo = new SingleRoomInfoBo();
		singleRoomInfoBo.setStartTime(singleRoom.getStartTime());
		singleRoomInfoBo.setTrainingRecordData(missionsRecord);
		singleRoomInfoBo.setResourceId(singleRoom.getResourceId());
		singleRoomInfoBo
				.setRunningTime(TimeUtil.getIntervalSeconds(singleRoom.getStartTime(), new Date()));
		singleRoomBo.setRoomInfo(singleRoomInfoBo);

		return singleRoomBo;
	}

	/**
	 * 关闭单兵训练房间
	 *
	 * @param businessTemplateId 训练业务模板id
	 */
	@Override
	public Boolean deleteRoom(String businessTemplateId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, userId);
		return deleteRoom(singleRoom);
	}

	/**
	 * 关闭单兵训练房间
	 *
	 * @param singleRoom 训练房间对象
	 */
	@Override
	public Boolean deleteRoom(SingleRoom singleRoom) {

		if (singleRoom == null) {
			return false;
		}

		// 关闭场景，不应该被mp执行结果所堵塞
		try {
			localSceneService.stopScene(singleRoom.getLocalSceneId());
		} catch (Exception e) {
			log.error("Failed to delete mp scene", e);
		}
		// 删除单兵训练房间, 记录结束时间
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				localSceneService.removeById(singleRoom.getLocalSceneId());
				singleRoomMapper.deleteById(singleRoom);
				trainRecordService.updateEndDate(singleRoom.getResourceId(),
						BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
				crRoomDeviceApplyService.releaseApply(singleRoom.getResourceId());
			}
		});
		return true;
	}

	/**
	 * 前台单兵训练页面
	 *
	 * @param queryParams 查询条件
	 * @return 单兵训练列表
	 */
	@Override
	public ListRsp<SingleRoomListBo> getSingleTrainPageList(SingleTrainListReq queryParams) {
		// 分页查询结果
		IPage<SingleRoomList> selectResult = singleRoomMapper.singleTrainSearch(
				queryParams.toPageParam(), UserInfoContext.getUserContext().getUserId(),
				queryParams.getPattern(), queryParams.getCategory(), queryParams.getDifficult(),
				BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
		if (selectResult.getRecords().isEmpty()) {
			return new ListRsp<>();
		}

		// 向前7天日期
		List<String> dateRange = Lists.newArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 7; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
			dateRange.add(0, dateFormat.format(calendar.getTime()));
		}
		// 日期截取到月份, 2021-01-01 -> 01-01
		List<String> dateRangeForMonth = dateRange.stream()
				.map(date -> date.substring(date.indexOf("-") + 1)).collect(Collectors.toList());

		// 当前页训练数据resource_id
		List<String> template_ids = selectResult.getRecords().stream()
				.map(singleRoomList -> singleRoomList.getBusinessTemplate().getResourceId())
				.collect(Collectors.toList());

		// 查询训练使用统计
		List<SingleTrainStats> dateStats = singleRoomMapper.singleTrainStats(template_ids,
				dateRange, BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
		Map<String, List<Integer>> trainStats = dateStats.stream().collect(
				Collectors.toMap(SingleTrainStats::getBusinessTemplateId, singleTrainStats -> {
					List<SingleTrainStats> currentTrainHistory = dateStats.stream()
							.filter(singleTrainStats1 -> singleTrainStats1.getBusinessTemplateId()
									.equals(singleTrainStats.getBusinessTemplateId()))
							.collect(Collectors.toList());
					return dateRangeForMonth.stream().map(date -> {
						Integer count = 0;
						for (SingleTrainStats dailyTrainStats : currentTrainHistory) {
							if (dailyTrainStats.getDate().equals(date)) {
								count = dailyTrainStats.getCount();
								break;
							}
						}
						return count;
					}).collect(Collectors.toList());
				}, (key1, key2) -> key1));

		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService
				.batchGetTopoDigest(selectResult.getRecords().stream()
						.map(singleRoomList -> singleRoomList.getBusinessTemplate().getTopoId())
						.collect(Collectors.toList()));

		// get attack engage relation
		List<String> attackRelatedIds = businessTemplateService.getAttackRelatedIds(template_ids);
		List<String> engageRelatedIds = businessTemplateService.getEngageRelatedIds(template_ids);

		// 拓展额外数据
		List<SingleRoomListBo> singleRoomListBoList = Lists.newArrayList();
		for (SingleRoomList item : selectResult.getRecords()) {
			SingleRoomListBo singleRoomListBo = new SingleRoomListBo();
			singleRoomListBo.setRoomInfo(new SingleRoomInfoBo());
			singleRoomListBo.setEventData(new EventDataBo());

			BeanUtils.copyProperties(item.getSingleTemplate(), singleRoomListBo.getEventData());
			BeanUtils.copyProperties(item.getBusinessTemplate(), singleRoomListBo.getEventData());
			if (item.getSingleRoom() != null) {
				BeanUtils.copyProperties(item.getSingleRoom(), singleRoomListBo.getRoomInfo());
				singleRoomListBo.getRoomInfo().setRunningTime(TimeUtil.getIntervalSeconds(
						singleRoomListBo.getRoomInfo().getStartTime(), new Date()));
			} else {
				singleRoomListBo.setRoomInfo(null);
			}
			singleRoomListBo.setNodesCountData(
					topologyNodesStats.get(item.getBusinessTemplate().getTopoId()).getNodeCounts());
			singleRoomListBo.setResourceOccupiedNums(
					topologyNodesStats.get(item.getBusinessTemplate().getTopoId()).getResourceOccupiedNums());

			singleRoomListBo.getEventData()
					.setDifficult(item.getBusinessTemplate().getDifficulty());
			singleRoomListBo.getEventData().setCategory(item.getSingleTemplate().getSubType());

			singleRoomListBo.setResourceId(item.getBusinessTemplate().getResourceId());
			singleRoomListBo.getEventData().setPreSkill(JsonUtil
					.toJsonObjectArray(item.getSingleTemplate().getPreSkills(), String.class));
			singleRoomListBo.getEventData().setTrainSkill(JsonUtil
					.toJsonObjectArray(item.getSingleTemplate().getTrainSkills(), String.class));

			singleRoomListBo.setLastSevenDays(dateRangeForMonth);
			singleRoomListBo.setTemplateUseCount(trainStats.getOrDefault(
					singleRoomListBo.getResourceId(), Lists.newArrayList(0, 0, 0, 0, 0, 0, 0)));

			// set attack engage relation
			singleRoomListBo.setIsAttackRelated(
					attackRelatedIds.contains(singleRoomListBo.getResourceId()));
			singleRoomListBo.setIsEngageRelated(
					engageRelatedIds.contains(singleRoomListBo.getResourceId()));

			singleRoomListBoList.add(singleRoomListBo);
		}

		// 生成分页结构
		ListRsp<SingleRoomListBo> result = new ListRsp<>();
		result.setList(singleRoomListBoList);
		result.setTotal(selectResult.getTotal());
		result.setLastPage(selectResult.getPages());
		result.setCurrentPage(selectResult.getCurrent());

		return result;
	}

	/**
	 * 获取训练任务详情, 存在答题记录情况同任务信息一起返回
	 *
	 * @param businessTemplateId 训练业务模板id
	 * @param missionId          任务id
	 * @return SingleMissionBo
	 */
	@Override
	public SingleMissionBo getMission(String businessTemplateId, String missionId) {
		SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId,
				UserInfoContext.getUserContext().getUserId());

		SingleMissionBo singleMissionBo = new SingleMissionBo();

		// 获取任务基础信息。 单兵训练未运行, 获取模板任务信息, 并直接返回
		if (singleRoom == null) {
			MissionTemplate missionTemplate = missionService.getByMissionTemplateId(missionId);
			BeanUtils.copyProperties(missionTemplate, singleMissionBo);
			singleMissionBo.setCategory(
					missionService.getCategoryById(missionTemplate.getCategoryId()).getName());
			if (missionTemplate.getOption() != null) {
				singleMissionBo.setOption(
						JsonUtil.toJsonObjectArray(missionTemplate.getOption(), JSONObject.class));
			}
			return singleMissionBo;
		} else {
			Mission mission = missionService.getById(missionId);
			BeanUtils.copyProperties(mission, singleMissionBo);
			singleMissionBo
					.setCategory(missionService.getCategoryById(mission.getCategoryId()).getName());
			if (mission.getOption() != null) {
				singleMissionBo.setOption(
						JsonUtil.toJsonObjectArray(mission.getOption(), JSONObject.class));
			}
		}

		// 训练运行中, 尝试获取此任务训练记录
		JSONObject trainRecord = JsonUtil.toJsonObject(singleRoom.getTrainingRecord(),
				JSONObject.class);
		JSONObject missionRecord = trainRecord.getJSONObject(missionId);
		if (missionRecord != null) {
			missionRecord.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date(missionRecord.getLong("time"))));

			// 去除多余字段
			missionRecord.remove(SingleRoom.MissionsRecordFields.SCORE);
			missionRecord.remove(SingleRoom.MissionsRecordFields.RESULT);
		}

		singleMissionBo.setTrainingRecord(missionRecord);

		return singleMissionBo;
	}

	/**
	 * 训练任务提交
	 *
	 * @param businessTemplateId 训练业务模板id
	 * @param requestData        提交任务内容
	 * @return bool
	 */
	@Override
	public Boolean submitMission(String businessTemplateId, SubmitMissionReq requestData) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom roomObject = singleRoomMapper.getSingleRoom(businessTemplateId, userId);
		if (roomObject == null) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST_SUBMIT_MISSION);
		}

		// 单兵训练运行房间任务查询, 答题者即为创建者
		Mission mission = missionService.lambdaQuery()
				.eq(Mission::getRoomId, roomObject.getResourceId())
				.eq(MissionBase::getResourceId, requestData.getMissionResourceId())
				.eq(MissionBase::getCreateUserId, userId).one();
		if (mission == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}
		// 多选题答案校验
		if (mission.getType().equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())) {
			if (requestData.getAnswer().equals(CommonConstant.EMPTY_LIST_JSON)) {
				throw new BusinessException(CrBusinessErrorCode.ANSWER_NOT_NULLABLE);
			}
		}

		// 写入任务记录
		JSONObject missionsRecord = JsonUtil.toJsonObject(roomObject.getTrainingRecord(),
				JSONObject.class);

		JSONObject record = new JSONObject();
		record.put(SingleRoom.MissionsRecordFields.TIME, new Date());
		record.put(SingleRoom.MissionsRecordFields.ANSWER, requestData.getAnswer());
		record.put(SingleRoom.MissionsRecordFields.RECORD, requestData.getTrainingRecord());

		missionsRecord.put(requestData.getMissionResourceId(), record);

		roomObject.setTrainingRecord(JsonUtil.toJsonString(missionsRecord));
		updateById(roomObject);

		return true;
	}

	/**
	 * 当前用户此次单兵训练任务记录
	 *
	 * @param businessTemplateId 训练业务模板id
	 * @return 任务方案与任务训练记录
	 */
	@Override
	public List<TrainRecordBo> getTrainRecord(String businessTemplateId) {
		SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);

		// 获取任务方案
		JSONArray schemeData = JsonUtil
				.toJsonObject(singleTemplate.getMissionsScheme(), JSONObject.class)
				.getJSONArray("scheme_data");
		if (schemeData == null) {
			return null;
		}

		// 构建数据
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TrainRecordBo> trainRecordBos = Lists.newArrayList();
		SingleRoom roomObject = singleRoomMapper.getSingleRoom(businessTemplateId,
				UserInfoContext.getUserContext().getUserId());
		JSONObject trainRecord = roomObject != null
				? JsonUtil.toJsonObject(roomObject.getTrainingRecord(), JSONObject.class)
				: null;

		for (JSONObject stage : schemeData.toJavaList(JSONObject.class)) {
			TrainRecordBo trainRecordBo = new TrainRecordBo();
			trainRecordBo.setMissions(Lists.newArrayList());
			trainRecordBo.setStage(stage.getString("stage"));

			for (JSONObject _m : stage.getJSONArray("missions").toJavaList(JSONObject.class)) {
				MissionRecordBo missionRecordBo = new MissionRecordBo();
				missionRecordBo.setResourceId(_m.getString("resource_id"));
				missionRecordBo.setTitle(_m.getString("title"));
				missionRecordBo.setDescription(_m.getString("description"));

				if (trainRecord != null) {
					JSONObject missionRecord = trainRecord
							.getJSONObject(_m.getString("resource_id"));
					// 时间戳 -> 日期字符串
					if (missionRecord != null) {
						missionRecord.put("time",
								simpleDateFormat.format(new Date(missionRecord.getLong("time"))));
					}
					missionRecordBo.setTrainingRecord(missionRecord);
				}

				trainRecordBo.getMissions().add(missionRecordBo);
			}

			trainRecordBos.add(trainRecordBo);
		}

		return trainRecordBos;
	}

	/**
	 * 获取训练记录(markdown)
	 *
	 * @param businessTemplateId 训练业务模板id
	 * @return String
	 */
	@Override
	public String getTrainRecordMarkdown(String businessTemplateId) {
		// 无运行中房间, 返回null
		SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId,
				UserInfoContext.getUserContext().getUserId());
		if (singleRoom == null) {
			return null;
		}
		SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);
		BusinessTemplate businessTemplate = businessTemplateService.getById(businessTemplateId);

		// 获取任务方案
		JSONArray schemeData = JsonUtil
				.toJsonObject(singleTemplate.getMissionsScheme(), JSONObject.class)
				.getJSONArray("scheme_data");
		JSONObject trainRecord = JsonUtil.toJsonObject(singleRoom.getTrainingRecord(),
				JSONObject.class);
		StringBuilder trainRecordMd = new StringBuilder();
		trainRecordMd.append("# <center> ").append(businessTemplate.getName())
				.append(" </center> \n\n");
		trainRecordMd.append(String.format("###### %s: ", TrainI18n.PEOPLE)).append(UserInfoContext.getUserContext().getUserName())
				.append("\n\n");
		trainRecordMd.append(String.format("###### %s: ", TrainI18n.TIME)).append(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(singleRoom.getStartTime()))
				.append("\n\n\n");
		trainRecordMd.append(String.format("## %s%n%n", TrainI18n.SCENE_DESC)).append(businessTemplate.getDescription())
				.append("\n\n");

		// 生成训练记录富文本
		List<JSONObject> stages = schemeData.toJavaList(JSONObject.class);
		for (int i = 0; i < stages.size(); i++) {
			JSONObject stage = stages.get(i);
			trainRecordMd.append(String.format("## %s", TrainI18n.STAGE)).append(i + 1).append(": ")
					.append(stage.getString("stage")).append("\n\n");
			List<JSONObject> missions = stage.getJSONArray("missions").toJavaList(JSONObject.class);
			for (int j = 0; j < missions.size(); j++) {
				JSONObject mission = missions.get(j);
				trainRecordMd.append(String.format("#### %s", TrainI18n.TASK)).append(j + 1).append(": ")
						.append(mission.getString("title")).append("\n\n");
				trainRecordMd.append(String.format("> %s%n%n", TrainI18n.TASK_STAT)).append(mission.getString("description"))
						.append("\n\n").append(String.format("> %s%n%n", TrainI18n.MY_RECORD));
				JSONObject singleMissionRecord = trainRecord
						.getJSONObject(mission.getString("resource_id"));
				if (singleMissionRecord != null) {
					trainRecordMd
							.append(singleMissionRecord
									.getString(SingleRoom.MissionsRecordFields.RECORD))
							.append("\n");
				}
			}
			trainRecordMd.append("\n");
		}

		return trainRecordMd.toString();
	}

	/**
	 * 根据任务方案、训练记录生成富文本
	 *
	 * @param businessTemplateId 训练业务模板id
	 * @return String
	 */
	@Override
	public String getTrainManual(String businessTemplateId) {
		// 获取训练方案
		SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);
		CrRoleConfigMissionsScheme missionScheme = JsonUtil
				.toJsonObject(singleTemplate.getMissionsScheme(), CrRoleConfigMissionsScheme.class);
		List<CrRoleConfigSchemeStage> stages = missionScheme.getSchemeData();
		Map<String, MissionTemplate> missionTemplateMap = missionTemplateService
				.getMappingBySchemeJson(singleTemplate.getMissionsScheme());

		// 生成富文本训练手册
		StringBuilder writeup = new StringBuilder();
		for (int i = 0; i < stages.size(); i++) {
			CrRoleConfigSchemeStage item = stages.get(i);

			writeup.append("## ").append(TrainI18n.STAGE.getMsg()).append(i + 1).append(": ").append(item.getStage())
					.append("\n\n");
			List<CrRoleConfigMission> missions = item.getMissions();
			List<String> missionTemplateIds = Lists.newArrayList();
			for (CrRoleConfigMission mission : missions) {
				missionTemplateIds.add(mission.getResourceId());
			}
			for (int j = 0; j < missions.size(); j++) {
				MissionTemplate missionTemplate = missionTemplateMap.get(missionTemplateIds.get(j));
				writeup.append("#### ").append(TrainI18n.TASK.getMsg()).append(j + 1).append(": ")
						.append(missionTemplate.getTitle()).append("\n\n");
				writeup.append(String.format("%s: %n", TrainI18n.WRITEUP)).append(missionTemplate.getWriteUp()).append("\n\n");
			}
		}

		return writeup.toString();
	}

	/**
	 * 获取训练时间线数据
	 *
	 * @param businessTemplateId 训练业务模板id
	 * @return TrainTimeLineBo
	 */
	@Override
	public TrainTimeLineBo getTimeLineData(String businessTemplateId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, userId);
		if (singleRoom == null) {
			return null;
		}

		SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);
		TrainTimeLineBo trainTimeLineBo = new TrainTimeLineBo();

		trainTimeLineBo.setCurrentTime(new Date());
		trainTimeLineBo.setStartTime(singleRoom.getStartTime());

		// 预计结束时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(singleRoom.getStartTime());
		calendar.add(Calendar.HOUR, singleTemplate.getDurationTime());
		trainTimeLineBo.setEndTime(calendar.getTime());

		// 任务完成时间点
		List<JSONObject> missionRecord = Lists.newArrayList();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 获取用户任务
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(singleRoom.getResourceId(),
				userId);
		if (crRole == null) {
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		Map<String, Mission> missionMap = getMissionMapByScheme(crRole.getMissionStageSchemes());

		JSONObject trainRecord = JsonUtil.toJsonObject(singleRoom.getTrainingRecord(),
				JSONObject.class);
		for (Map.Entry<String, Object> stringObjectEntry : trainRecord.entrySet()) {

			JSONObject missionEvent = new JSONObject();
			JSONObject val = (JSONObject) stringObjectEntry.getValue();
			missionEvent.put("time", simpleDateFormat.format(new Date(val.getLong("time"))));
			missionEvent.put("title", missionMap.get(stringObjectEntry.getKey()).getTitle());

			missionRecord.add(missionEvent);
		}
		trainTimeLineBo.setData(missionRecord);

		return trainTimeLineBo;
	}

	@Override
	public SceneViewContext viewScene(String businessTemplateId) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(businessTemplateId);
		if (businessTemplate == null) {
			return null;
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = singleRoomMapper.getSingleRoom(businessTemplateId, userId);

		SceneViewContext ret = new SceneViewContext();
		if (room != null) {
			ret.setRoomId(room.getResourceId());
		}
		// 1、尝试获取启动的远程场景
		JSONObject sceneData = getMpSceneData(room);
		if (sceneData != null) {
			CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(room.getResourceId(), userId);
			if (crRole != null) {
				sceneFilterService.filterApplyDevice(sceneData, crRole);
			}
			ret.setSceneData(sceneData);
			ret.setLocalSceneId(room.getLocalSceneId());
			ret.setMpSceneId(sceneData.getString(MP_SCENE_ID_KEY));
			return ret;
		}
		// 2、 远程场景不存在，返回topo数据
		JSONObject topoData = localSceneService.getTopoData(businessTemplate.getTopoId())
				.getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
		ret.setTopoData(topoData);
		return ret;
	}

	@Override
	public SingleRoom getByBusinessTemplateIdAndUserId(String businessTemplateId, String userId) {
		return singleRoomMapper.getSingleRoom(businessTemplateId, userId);
	}

	@Override
	public SingleRoom getByBusinessId(String businessId) {
		String[] ids = StringUtils.split(businessId, "_", 2);
		if (ids.length != 2) {
			log.error(String.format("invalid business id for single training: %s", businessId));
			return null;
		}
		return singleRoomMapper.getSingleRoom(ids[0], ids[1]);
	}

	private JSONObject getMpSceneData(SingleRoom room) {
		if (room == null) {
			return null;
		}
		String localSceneId = room.getLocalSceneId();
		// localSceneId字段为空
		if (StringUtils.isBlank(localSceneId)) {
			return null;
		}
		// localScene不存在，删除room
		LocalScene localScene = localSceneService.getById(localSceneId);
		if (null == localScene) {
			removeById(room.getResourceId());
			return null;
		}
		// localScene 的 mpSceneId为空，localScene是脏数据
		String mpSceneId = localScene.getMpSceneId();
		if (StringUtils.isBlank(mpSceneId)) {
			clearLocal(room);
			return null;
		}
		// 请求远程场景数据
		JSONObject sceneData = localSceneService.getSceneData(mpSceneId,
				UserInfoContext.getUserContext().getUserId());
		// 场景数据为空，清除本地脏数据
		if (null == sceneData) {
			clearLocal(room);
			return null;
		}
		// 场景实际已被删除，清除本地脏数据
		SceneData data = sceneData.toJavaObject(SceneData.class);
		if (data.isStopped()) {
			clearLocal(room);
			return null;
		}
		sceneData.put(MP_SCENE_ID_KEY, mpSceneId);
		return sceneData;
	}

	@Override
	public void clearLocal(SingleRoom room) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				localSceneService.removeById(room.getLocalSceneId());
				removeById(room.getResourceId());
			}
		});
	}

	@Override
	public List<SingleRoom> getByBusinessTemplateId(String businessTemplateId) {
		return singleRoomMapper.selectByTemplateId(businessTemplateId);
	}

	@Override
	public List<TrainHistoryBo> getTrainHistory(String businessTemplateId) {
		List<TrainRecord> trainHistory = trainRecordService.lambdaQuery()
				.eq(TrainRecord::getUserId, UserInfoContext.getUserContext().getUserId())
				.eq(TrainRecord::getBusinessTemplateId, businessTemplateId)
				.isNotNull(TrainRecord::getEndTime).isNotNull(TrainRecord::getExtra)
				.orderByAsc(TrainRecord::getEndTime).list();

		List<TrainHistoryBo> result = Lists.newArrayList();
		for (int i = 0; i < trainHistory.size(); i++) {
			TrainRecord item = trainHistory.get(i);
			TrainHistoryBo trainHistoryBo = new TrainHistoryBo();
			trainHistoryBo.setRoomId(item.getRoomId());
			trainHistoryBo.setTrainTime(item.getEndTime());
			trainHistoryBo.setTrainTimes(i + 1);

			JSONObject trainExtraData = JsonUtil.toJsonObject(item.getExtra(), JSONObject.class);
			if (trainExtraData.isEmpty()) {
				trainHistoryBo.setScore(0d);
			} else {
				trainHistoryBo
						.setScore(trainExtraData.getDouble(SingleRoom.CrTrainRecordFields.SCORE));
			}

			result.add(trainHistoryBo);
		}
		Collections.reverse(result);
		return result;
	}

	@Override
	public String submitTrain(String businessTemplateId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, userId);
		// 无运行中训练、训练已完成
		if (singleRoom == null || singleRoom.getIsFinish()) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		JSONObject extraRecord = new JSONObject();

		JSONObject singleTrainRecord = JsonUtil.toJsonObject(singleRoom.getTrainingRecord(),
				JSONObject.class);
		Map<String, String> userAnswer = Maps.newHashMap();
		if (!singleTrainRecord.isEmpty()) {
			// 用户训练记录
			extraRecord.put(SingleRoom.CrTrainRecordFields.MISSIONS_RECORD, singleTrainRecord);

			singleTrainRecord.forEach((key, val) -> {
				JSONObject missionResult = (JSONObject) val;
				userAnswer.put(key,
						missionResult.getString(SingleRoom.MissionsRecordFields.ANSWER));
			});
		}

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(singleRoom.getResourceId(),
				userId);
		if (crRole == null) {
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 任务提交, 评分
				Integer score = crTaskService.batchSubmitMissions(singleRoom.getResourceId(),
						missionService.getMissionIdsByScheme(crRole.getMissionStageSchemes()),
						userAnswer);

				// 写入训练记录
				if (!extraRecord.isEmpty()) {
					extraRecord.put(SingleRoom.CrTrainRecordFields.SCORE, score);
				}
				trainRecordService.updateEndDate(businessTemplateId, userId,
						BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode(),
						JsonUtil.toJsonString(extraRecord));

				// 设置当前训练已完成
				singleRoom.setIsFinish(true);
				singleRoom.setEndTime(new Date());
				singleRoomMapper.updateById(singleRoom);
			}
		});

		return singleRoom.getResourceId();
	}

	private Map<String, Mission>
			getMissionMapByScheme(List<MissionStageScheme> missionStageSchemes) {
		List<String> missionIds = missionService.getMissionIdsByScheme(missionStageSchemes);

		if (missionIds.isEmpty()) {
			return Maps.newHashMap();
		}

		return missionService.batchGet(missionIds).stream()
				.collect(Collectors.toMap(MissionBase::getResourceId, mission -> mission));
	}

}
