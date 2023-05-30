package com.cyberpeace.cr.singletrain.service.impl;

import static com.cyberpeace.cr.crbase.constant.CrCommonConstants.MP_SCENE_ID_KEY;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.TrainI18n;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.manual.ManualMissionBo;
import com.cyberpeace.cr.crbase.service.context.manual.ManualStageBo;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.cr.crbase.service.context.score.RecordStatistics;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
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
import com.cyberpeace.cr.singletrain.service.bo.mission.MatrixMission;
import com.cyberpeace.cr.singletrain.service.bo.mission.MatrixStage;
import com.cyberpeace.cr.singletrain.service.bo.mission.SingleMatrixProgressRsp;
import com.cyberpeace.cr.singletrain.service.bo.record.RecordDetail;
import com.cyberpeace.cr.singletrain.service.bo.record.RecordMissionSimpleBo;
import com.cyberpeace.cr.singletrain.service.bo.record.RecordStageBo;
import com.cyberpeace.cr.singletrain.service.bo.record.TrainingRecordListBo;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.IdNameType;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.integratation.model.scene.SceneNodeListReq;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
	private RtksRelatedService rtksRelatedService;

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
			log.error("scene start repeat: businessTemplateId = [{}]",businessTemplateId);
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
		Map<String, List<NameCount>> attackRelCount = rtksRelatedService
				.countAttackRel(template_ids);
		Map<String, List<NameCount>> icsRelCount = rtksRelatedService.countIcsRel(template_ids);
		Map<String, List<NameCount>> engageRelCount = rtksRelatedService
				.countEngageRel(template_ids);

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
			singleRoomListBo.setResourceOccupiedNums(topologyNodesStats
					.get(item.getBusinessTemplate().getTopoId()).getResourceOccupiedNums());

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
					attackRelCount.containsKey(singleRoomListBo.getResourceId()));
			singleRoomListBo.setAttackCount(attackRelCount
					.getOrDefault(singleRoomListBo.getResourceId(), Lists.newArrayList()));
			singleRoomListBo
					.setIsIcsRelated(icsRelCount.containsKey(singleRoomListBo.getResourceId()));
			singleRoomListBo.setIcsCount(icsRelCount.getOrDefault(singleRoomListBo.getResourceId(),
					Lists.newArrayList()));
			singleRoomListBo.setIsEngageRelated(
					engageRelCount.containsKey(singleRoomListBo.getResourceId()));
			singleRoomListBo.setEngageCount(engageRelCount
					.getOrDefault(singleRoomListBo.getResourceId(), Lists.newArrayList()));

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
			log.error("room_not_exist_submit_mission: businessTemplateId = [{}]",businessTemplateId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST_SUBMIT_MISSION);
		}

		// 单兵训练运行房间任务查询, 答题者即为创建者
		Mission mission = missionService.lambdaQuery()
				.eq(Mission::getRoomId, roomObject.getResourceId())
				.eq(MissionBase::getResourceId, requestData.getMissionResourceId())
				.eq(MissionBase::getCreateUserId, userId).one();
		if (mission == null) {
			log.error("mission is null: RoomId = [{}],ResourceId= [{}]",roomObject.getResourceId(), requestData.getMissionResourceId());
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}
		// 多选题答案校验
		if (mission.getType().equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())) {
			if (requestData.getAnswer().equals(CommonConstant.EMPTY_LIST_JSON)) {
				log.error("answer not nullable");
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
		trainRecordMd.append(String.format("###### %s: ", TrainI18n.PEOPLE))
				.append(UserInfoContext.getUserContext().getUserName()).append("\n\n");
		trainRecordMd.append(String.format("###### %s: ", TrainI18n.TIME)).append(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(singleRoom.getStartTime()))
				.append("\n\n\n");
		trainRecordMd.append(String.format("## %s%n%n", TrainI18n.SCENE_DESC))
				.append(businessTemplate.getDescription()).append("\n\n");

		// 生成训练记录富文本
		List<JSONObject> stages = schemeData.toJavaList(JSONObject.class);
		for (int i = 0; i < stages.size(); i++) {
			JSONObject stage = stages.get(i);
			trainRecordMd.append(String.format("## %s", TrainI18n.STAGE)).append(i + 1).append(": ")
					.append(stage.getString("stage")).append("\n\n");
			List<JSONObject> missions = stage.getJSONArray("missions").toJavaList(JSONObject.class);
			for (int j = 0; j < missions.size(); j++) {
				JSONObject mission = missions.get(j);
				trainRecordMd.append(String.format("#### %s", TrainI18n.TASK)).append(j + 1)
						.append(": ").append(mission.getString("title")).append("\n\n");
				trainRecordMd.append(String.format("> %s%n%n", TrainI18n.TASK_STAT))
						.append(mission.getString("description")).append("\n\n")
						.append(String.format("> %s%n%n", TrainI18n.MY_RECORD));
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
	public List<ManualStageBo> getTrainManual(String businessTemplateId) {
		List<ManualStageBo> res = Lists.newArrayList();
		// get role and mission scheme
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = getByBusinessTemplateIdAndUserId(businessTemplateId, userId);
		if (null == room) {
			return res;
		}
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(room.getResourceId(), userId);
		List<CrRoleMissionSchemeStage> stages = JsonUtil.toJsonObjectArray(role.getMissionScheme(),
				CrRoleMissionSchemeStage.class);
		List<Mission> missions = missionService.batchGetByRoom(room.getResourceId());
		Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getResourceId,
				mission -> mission);

		// 遍历stage
		for (CrRoleMissionSchemeStage stage : stages) {
			ManualStageBo stageBo = new ManualStageBo();
			stageBo.setStageName(stage.getStage());
			for (String missionId : stage.getMissions()) {
				Mission mission = missionMap.get(missionId);
				if (null == mission) {
					continue;
				}
				ManualMissionBo missionBo = new ManualMissionBo();
				missionBo.setResourceId(missionId);
				missionBo.setTitle(mission.getTitle());
				missionBo.setWriteUp(mission.getWriteUp());

				stageBo.getMissions().add(missionBo);
			}
			res.add(stageBo);
		}

		return res;
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
			log.error("role not exist error: ResourceId = [{}]",singleRoom.getResourceId());
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
			missionEvent.put("userName",UserInfoContext.getUserContext().getUserName());
			missionEvent.put("avatarUrl",UserInfoContext.getUserContext().getAvatarUrl());

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
	public List<TrainingRecordListBo> getTrainHistory(String businessTemplateId) {
		// get records
		List<TrainRecord> trainHistory = trainRecordService.lambdaQuery()
				.eq(TrainRecord::getUserId, UserInfoContext.getUserContext().getUserId())
				.eq(TrainRecord::getBusinessTemplateId, businessTemplateId)
				.isNotNull(TrainRecord::getEndTime).isNotNull(TrainRecord::getExtra)
				.orderByAsc(TrainRecord::getEndTime).list();

		if (trainHistory.isEmpty()) {
			return Lists.newArrayList();
		}

		// get total mission and total score
		List<MissionTemplate> missionTemplateList = getMissionTemplateListByBusinessTemplateId(
				businessTemplateId);
		Integer missionCount = missionTemplateList.size();
		Integer total = missionTemplateList.stream().mapToInt(MissionBase::getScore).sum();

		List<TrainingRecordListBo> result = Lists.newArrayList();
		for (int i = 0; i < trainHistory.size(); i++) {
			TrainRecord trainRecord = trainHistory.get(i);
			TrainingRecordListBo bo = new TrainingRecordListBo();
			// roomId
			bo.setRoomId(trainRecord.getRoomId());
			// training time = end time
			bo.setTrainingTime(trainRecord.getStartTime());
			// duration
			String duration = TimeUtil.getTimeInterval(trainRecord.getStartTime(),
					trainRecord.getEndTime());
			bo.setTimeCost(duration);
			// index
			bo.setIndex(i + 1);
			// get score and finished mission count from extra
			JSONObject trainExtraData = JsonUtil.toJsonObject(trainRecord.getExtra(),
					JSONObject.class);
			if (trainExtraData.isEmpty()) {
				bo.setScore(0);
				bo.setFinished(0);
			} else {
				bo.setScore(trainExtraData.getInteger(SingleRoom.CrTrainRecordFields.SCORE));
				bo.setFinished(trainExtraData
						.getJSONObject(SingleRoom.CrTrainRecordFields.MISSIONS_RECORD).size());
			}
			// set total
			bo.setTotalScore(total);
			bo.setMissionCount(missionCount);
			result.add(bo);
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
			log.error("room not exist error: businessTemplateId = [{}]",businessTemplateId);
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
			log.error("crRole not exist error: ResourceId = [{}]",singleRoom.getResourceId());
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

	@Override
	public RecordDetail getRecordDetail(String roomId) {
		// init
		RecordDetail detail = new RecordDetail();
		List<Integer> pointsAll = CrCommonUtil.getInitPoints();
		List<Integer> pointsGet = CrCommonUtil.getInitPoints();
		Integer successful = 0;
		Integer failed = 0;
		RecordStatistics statistics = new RecordStatistics();

		TrainRecord trainRecord = trainRecordService.getByRoomId(roomId);
		// get template
		String btId = trainRecord.getBusinessTemplateId();
		BusinessTemplate bt = businessTemplateService.getById(btId);
		SingleTemplate singleTemplate = singleTemplateService.getByBusinessTemplateId(btId);
		// set template detail
		detail.setTrainingName(bt.getName());
		detail.setTraineeName(UserInfoContext.getUserContext().getName());
		detail.setDurationTime(singleTemplate.getDurationTime());
		detail.setSubType(singleTemplate.getSubType());
		detail.setDifficulty(bt.getDifficulty());
		detail.setTrainSkill(
				JsonUtil.toJsonObjectArray(singleTemplate.getTrainSkills(), String.class));
		detail.setPreSkill(JsonUtil.toJsonObjectArray(singleTemplate.getPreSkills(), String.class));
		detail.setDescription(bt.getDescription());
		detail.setTrainingTime(trainRecord.getStartTime());
		String duration = TimeUtil.getTimeInterval(trainRecord.getStartTime(),
				trainRecord.getEndTime());
		detail.setTimeCost(duration);
		// get role -> get stages structure -> get missions
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId,
				UserInfoContext.getUserContext().getUserId());
		List<MissionStageScheme> missionStages = JsonUtil.toJsonObjectArray(role.getMissionScheme(),
				MissionStageScheme.class);
		List<String> missionIds = missionService.getMissionIdsByScheme(missionStages);
		List<Mission> missionList = missionService.batchGet(missionIds);
		Map<String, Mission> missionMap = StreamApiUtils.toMap(missionList, Mission::getResourceId,
				mission -> mission);
		// mission count
		statistics.setMissionCount(missionList.size());
		// get record extra -> get score -> get recorded user answer
		JSONObject extra = JsonUtil.toJsonObject(trainRecord.getExtra(), JSONObject.class);
		if (extra.isEmpty()) {
			detail.setScore(0);
		} else {
			detail.setScore(extra.getInteger(SingleRoom.CrTrainRecordFields.SCORE));
		}
		// 不包含任务
		if (missionList.isEmpty()) {
			statistics.setSuccessful(0);
			statistics.setFailed(0);
			statistics.calculate();
			detail.setRecordStatistics(statistics);
			detail.setRadars(CrCommonUtil.getInitPoints());
			return detail;
		}
		// get recorded user answer
		JSONObject missionRecordCol = extra
				.getJSONObject(SingleRoom.CrTrainRecordFields.MISSIONS_RECORD);
		for (MissionStageScheme stage : missionStages) {
			RecordStageBo stageBo = new RecordStageBo();
			stageBo.setStageName(stage.getStage());
			for (String missionId : stage.getMissions()) {
				// get mission
				Mission mission = missionMap.get(missionId);
				if (null == mission) {
					continue;
				}
				// collect radar
				List<Integer> points = JsonUtil.toJsonObjectArray(mission.getSkillPoints(),
						Integer.class);
				CrCommonUtil.integratePoints(pointsAll, points);

				RecordMissionSimpleBo missionBo = new RecordMissionSimpleBo();
				missionBo.setTitle(mission.getTitle());
				missionBo.setDescription(mission.getDescription());
				missionBo.setType(mission.getType());
				// option
				if (StringUtils.isNotBlank(mission.getOption())) {
					missionBo.setOption(
							JsonUtil.toJsonObjectArray(mission.getOption(), JSONObject.class));
				}
				missionBo.setAnswer(mission.getAnswer());
				// get user answer
				JSONObject missionRecord = missionRecordCol.getJSONObject(missionId);
				if (null == missionRecord) {
					// 未作答
					missionBo.setStatus(CrTask.SingleTaskStatus.RELEASED.getCode());
				} else {
					String userAnswer = missionRecord
							.getString(SingleRoom.MissionsRecordFields.ANSWER);
					missionBo.setUserAnswer(userAnswer);
					// judge
					if (userAnswer.equals(mission.getAnswer())) {
						CrCommonUtil.integratePoints(pointsGet, points);
						successful += 1;
						missionBo.setStatus(CrTask.SingleTaskStatus.SUCCESS.getCode());
					} else {
						failed += 1;
						missionBo.setStatus(CrTask.SingleTaskStatus.FAIL.getCode());
					}
				}

				stageBo.getMissions().add(missionBo);
			}
			detail.getStages().add(stageBo);
		}
		// radar
		detail.setRadars(CrCommonUtil.calculateRadar(pointsAll, pointsGet));
		// statistics
		statistics.setSuccessful(successful);
		statistics.setFailed(failed);
		statistics.calculate();
		detail.setRecordStatistics(statistics);

		return detail;
	}

	@Override
	public SingleMatrixProgressRsp getMatrixProgress(SingleRoom room) {
		SingleMatrixProgressRsp rsp = new SingleMatrixProgressRsp();
		Integer score = 0;
		Integer successful = 0;
		Integer failed = 0;
		// get role
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(room.getResourceId(),
				UserInfoContext.getUserContext().getUserId());
		// get mission scheme
		List<MissionStageScheme> stages = JsonUtil.toJsonObjectArray(role.getMissionScheme(),
				MissionStageScheme.class);
		// get mission map
		Map<String, Mission> missionMap = getMissionMapByScheme(stages);
		// get tasks,单兵可重复提交，提交答案不影响crTask状态，答案从room的training_record获取
		JSONObject recordMap = JsonUtil.toJsonObject(room.getTrainingRecord(), JSONObject.class);

		for (MissionStageScheme stage : stages) {
			MatrixStage matrixStage = new MatrixStage();
			matrixStage.setStageName(stage.getStage());
			for (String missionId : stage.getMissions()) {
				Mission mission = missionMap.get(missionId);
				if (null == mission) {
					continue;
				}
				MatrixMission matrixMission = new MatrixMission();
				matrixMission.setResourceId(missionId);
				matrixMission.setTitle(mission.getTitle());

				JSONObject missionRecord = recordMap.getJSONObject(missionId);
				if (null == missionRecord) {
					// not answered
					matrixMission.setStatus(CrTask.SingleTaskStatus.RELEASED.getCode());
				} else {
					// get user answer
					String userAnswer = missionRecord
							.getString(SingleRoom.MissionsRecordFields.ANSWER);
					if (userAnswer.equals(mission.getAnswer())) {
						// right
						matrixMission.setStatus(CrTask.SingleTaskStatus.SUCCESS.getCode());
						score += mission.getScore();
						successful += 1;
					} else {
						// wrong
						matrixMission.setStatus(CrTask.SingleTaskStatus.FAIL.getCode());
						failed += 1;
					}
				}
				matrixStage.getMissions().add(matrixMission);
			}
			rsp.getMatrix().add(matrixStage);
		}

		rsp.setScore(score);
		rsp.setSuccessful(successful);
		rsp.setFailed(failed);
		rsp.setFinished(successful + failed);
		rsp.setMissionCount(missionMap.size());
		return rsp;
	}

	@Override
	public SingleMatrixProgressRsp getMatrixRsp(String btId) {

		SingleMatrixProgressRsp result;
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = getByBusinessTemplateIdAndUserId(btId, userId);
		if (null == room) {
			// training not started
			result = singleTemplateService.getSingleMatrix(btId);
			result.setSuccessful(0);
			result.setFailed(0);
			result.setFinished(0);
			result.setScore(0);
			// 训练时长为0
			result.setTimeCost(TimeUtil.getTimeInterval(new Date(0), new Date(0)));
			result.setStarted(false);
		} else {
			// training started
			result = getMatrixProgress(room);
			result.setTimeCost(TimeUtil.getTimeInterval(room.getCreateTime(), new Date()));
			result.setStarted(true);
		}
		// set user_info
		result.setUserId(userId);
		result.setUserName(UserInfoContext.getUserContext().getName());
		result.setUserAvatar(UserInfoContext.getUserContext().getAvatarUrl());

		return result;
	}

	/**
	 * 获取资产列表
	 * 不用考虑角色
	 *
	 * @param pageReq 分页请求体
	 * @return {@code ListRsp<JSONObject>}
	 */
	@Override
	public ListRsp<JSONObject> getAssetList(AssetPageReq pageReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		String businessInstanceId = pageReq.getResource_id() + "_" + userId;
		SceneNodeListReq req = new SceneNodeListReq();
		String mpSceneId = localSceneService.getMpSceneIdByBusinessInstanceId(businessInstanceId);
		req.setId(mpSceneId);
		req.setName(pageReq.getPattern());
		req.setSystemTypeName(pageReq.getSystemTypeName());
		List<JSONObject> rsp = localSceneService.getAssetList(req);
		return PageUtil.pageList(rsp, pageReq.getPage(), pageReq.getPerPage());
	}

	@Override
	public List<IdNameType> getOsNames(String businessTemplateId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		String businessInstanceId = businessTemplateId + "_" + userId;
		List<JSONObject> assetList = localSceneService
				.getAllAssetByBusinessInstanceId(businessInstanceId);

		return CrCommonUtil.getOsListFromAssets(assetList);
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

	/**
	 * 通过业务模板id获取任务模板列表
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code List<MissionTemplate>}
	 */
	private List<MissionTemplate>
			getMissionTemplateListByBusinessTemplateId(String businessTemplateId) {
		SingleTemplate singleTemplate = singleTemplateService
				.getByBusinessTemplateId(businessTemplateId);
		List<String> missionTemplateIds = singleTemplateService
				.getMissionTemplateIdsByMissionsScheme(singleTemplate.getMissionsScheme());
		return missionTemplateService.getBatchByIds(missionTemplateIds);
	}

}
