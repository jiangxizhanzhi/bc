package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.dao.entity.ClassroomScore;
import com.cyberpeace.cs.dao.entity.GxLocalOrg;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.ClassroomScoreMapper;
import com.cyberpeace.cs.dao.mapper.GxLocalOrgMapper;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.cs.service.context.GxTaskListContext;
import com.cyberpeace.cs.service.context.GxTaskListQuery;
import com.cyberpeace.integratation.model.gx.TrainResult;
import com.cyberpeace.integratation.model.gx.TrainScore;
import com.cyberpeace.integratation.model.mp.RemoteUser;
import com.cyberpeace.integratation.service.RemoteTaskService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.context.MpUserIds;
import com.cyberpeace.integratation.service.context.MpUserSlimInfo;
import com.cyberpeace.integratation.service.context.RemoteUserInfo;
import com.cyberpeace.integratation.service.context.RemoteUserInfos;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class GxTaskServiceImpl extends ServiceImpl<GxTaskMapper, GxTask> implements GxTaskService {

	private static final Logger log = LoggerFactory.getLogger("gx");

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@Autowired
	private GxLocalOrgMapper gxLocalOrgMapper;

	@Autowired
	private RemoteTaskService remoteTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private ClassroomScoreMapper classroomScoreMapper;

	private String SOFT_ID;

	@PostConstruct
	public void init() {
		SOFT_ID = SystemPropUtil.getProp("SOFT_ID");
	}

	@Override
	public Boolean create(GxTaskContext gxTaskContext) {

		// 校验任务id是否重复
		if (gxTaskMapper.getByTaskId(gxTaskContext.getTaskId()) != null) {
			log.info(String.format("Skip existing task, taskId: %s", gxTaskContext.getTaskId()));
			return false;
		}

		GxTask task = BeanUtils.copy(gxTaskContext, GxTask.class);

		save(task);

		return true;
	}

	@Override
	public IPage<GxTaskListContext> list(GxTaskListQuery query) {

		Page page = query.toPageParam();

		IPage<GxTaskListContext> data = gxTaskMapper.listTasks(page, query);

		return data;

	}

	@Override
	public String createTaskOrg(List<String> userList, String classRoomId, String orgName) {

		GxLocalOrg gxLocalOrg = new GxLocalOrg();
		gxLocalOrg.setClassRoomId(classRoomId);
		gxLocalOrg.setUserIds(JsonUtil.toJsonString(userList));
		gxLocalOrg.setOrgName(orgName);
		gxLocalOrgMapper.insert(gxLocalOrg);

		return gxLocalOrg.getResourceId();
	}

	@Override
	public List<String> getLocalUserList(GxLocalOrg gxLocalOrg) {
		List<String> userList = Lists.newArrayList();
		if (StringUtils.isNotEmpty(gxLocalOrg.getUserIds())) {
			userList = JsonUtil.toJsonObjectArray(gxLocalOrg.getUserIds(), String.class);
		}
		return userList;
	}

	@Override
	public Boolean clearClassRoomOrg(String classRoomId) {

		List<String> orgs = gxLocalOrgMapper.getByClassRoomId(classRoomId);

		if (orgs != null && !orgs.isEmpty()) {
			gxLocalOrgMapper.deleteBatchIds(orgs);
		}

		return true;

	}

	@Override
	public GxTaskContext getContextByData(JSONObject userTask, String token) {

		// 提取原始数据
		JSONObject taskBaseData = userTask.getJSONObject("taskBaseData");
		JSONObject taskDetails = userTask.getJSONObject("weekPlanAndWeekPlanDetail")
				.getJSONObject("weekPlanDetailDTO");
		String taskId = taskBaseData.getString("taskId");
		Date startTime = TimeUtil.toDate4(taskBaseData.getString("trainDate"));
		Double duration = taskDetails.getDouble("trainDuration");

		// 构造任务数据
		GxTaskContext gxTaskContext = new GxTaskContext();
		gxTaskContext.setTaskId(taskId);
		gxTaskContext.setName(taskBaseData.getString("taskName"));
		gxTaskContext.setContent(taskBaseData.getString("trainContent"));
		gxTaskContext.setTarget(taskBaseData.getString("trainTarget"));
		gxTaskContext.setDuration(duration);
		gxTaskContext.setStartTime(startTime);
		gxTaskContext.setEndTime(TimeUtil.addMins(startTime, (int) (duration * 60)));
		gxTaskContext.setValidityPeriod(taskBaseData.getInteger("validityPeriod"));

		// 处理任务用户数据
		if (!setTrainees(gxTaskContext, taskId, token)) {
			return null;
		}

		return gxTaskContext;
	}

	@Override
	public GxTaskContext getContextByMsg(JSONObject message) {

		// 提取原始数据
		String taskId = message.getString("taskId");
		String taskName = message.getString("taskName");
		String trainContent = message.getString("trainContent");
		String target = (String) message.getOrDefault("target", "");
		Integer trainTime = message.getInteger("trainTime");
		Integer trainLength = message.getInteger("trainLength");
		String token = message.getString("token");

		// 处理原始数据
		Double duration = MathUtils.divide((double) trainLength, 60.00);
		Date startTime = new Date(trainTime);

		// 构造任务数据
		GxTaskContext gxTaskContext = new GxTaskContext();
		gxTaskContext.setTaskId(taskId);
		gxTaskContext.setName(taskName);
		gxTaskContext.setContent(trainContent);
		gxTaskContext.setTarget(target);
		gxTaskContext.setDuration(duration);
		gxTaskContext.setStartTime(startTime);
		gxTaskContext.setEndTime(TimeUtil.addMins(startTime, trainLength));

		// 处理任务用户数据
		setTrainees(gxTaskContext, taskId, token);

		return gxTaskContext;

	}

	private Boolean setTrainees(GxTaskContext gxTaskContext, String taskId, String token) {

		// 获取共性平台任务的用户列表
		String trainees = remoteTaskService.getTaskUsers(taskId, token);
		if (trainees == null) {
			return false;
		}

		List<JSONObject> traineeList = JsonUtil.toJsonObjectArray(trainees, JSONObject.class);
		Integer traineesCount = traineeList == null ? 0 : traineeList.size();

		// 获取远程、本地用户id映射表
		List<RemoteUserInfo> remoteUserInfos = Lists.newArrayList();
		traineeList.forEach(jsonObject -> {
			RemoteUserInfo remoteUserInfo = new RemoteUserInfo(jsonObject.getString("userId"),
					jsonObject.getString("userName"));
			remoteUserInfos.add(remoteUserInfo);
		});
		Map<String, String> gxMpUserIdMap = getGxMpUserIdMap(new RemoteUserInfos(remoteUserInfos));

		// 替换用户列表中的userId为中台用户Id
		for (JSONObject userInfo : traineeList) {
			userInfo.put("userId", gxMpUserIdMap.get(userInfo.getString("userId")));
		}

		gxTaskContext.setTrainees(traineeList.toString());
		gxTaskContext.setTraineesCount(traineesCount);
		return true;
	}

	private Map<String, String> getGxMpUserIdMap(RemoteUserInfos remoteUserInfos) {
		List<MpUserSlimInfo> userInfos = remoteUserService.getMpUserInfos(remoteUserInfos);
		Map<String, String> userIdMap = Maps.newHashMap();
		userInfos.forEach(userInfo -> {
			userIdMap.put(userInfo.getThirdUserId(), userInfo.getId());
		});
		return userIdMap;
	}

	@Override
	public List<UserInfo> getUserInfosByOrgId(String orgId) {

		// 首先校验本地组织
		GxLocalOrg gxLocalOrg = gxLocalOrgMapper.selectById(orgId);

		if (gxLocalOrg != null) {
			List<String> userIds = getLocalUserList(gxLocalOrg);
			return remoteUserService.getByIdList(userIds);
		}

		return remoteUserService.ListByOrgId(orgId, true);
	}

	@Override
	public Boolean finishClassroom(String lessonId) {

		String userId = UserInfoContext.getUserContext().getUserId();
		String thirdUserId = UserInfoContext.getUserContext().getThirdUserId();
		String token = UserInfoContext.getUserContext().getThirdToken();

		// token不存在时报错 todo

		ClassroomScore classroomScore = classroomScoreMapper.selectByLessonIdAndUserId(lessonId,
				userId);

		// 未找到记录时直接报错
		if (classroomScore == null) {
			return false;
		}

		Boolean stopResult = remoteTaskService.stopClassroom(classroomScore.getTaskId(),
				thirdUserId, token);

		if (stopResult) {
			// 课时学完后赋值100分
			classroomScore.setScore(100);
			classroomScoreMapper.updateById(classroomScore);
			return true;
		}

		return false;
	}

	@Override
	public TrainResult getTrainResult(GxTask gxTask, String userId) {

		List<ClassroomScore> classroomScores = classroomScoreMapper
				.listByClassroomIdAndUserId(gxTask.getEventId(), userId);

		// 获取任务下所有用户课程表分数
		List<TrainScore> trainScores = Lists.newArrayList();
		List<String> mpUserIds = Lists.newArrayList();
		classroomScores.forEach(classroomScore -> {
			mpUserIds.add(classroomScore.getUserId());
		});
		Map<String, String> mpGxUserIdMap = remoteUserService
				.getMpGxUserIdMap(new MpUserIds(mpUserIds));
		classroomScores.forEach(classroomScore -> {
			trainScores.add(new TrainScore(mpGxUserIdMap.get(classroomScore.getUserId()),
					classroomScore.getScore()));
		});

		TrainResult trainResult = new TrainResult(gxTask.getDuration(), gxTask.getTaskId(),
				trainScores);

		return trainResult;
	}

	@Override
	public Boolean finishClassroomTask(GxTask gxTask, String userId, String token) {
		TrainResult trainResult = getTrainResult(gxTask, userId);
		if (!remoteTaskService.stopClassroomTask(trainResult, token)) {
			return false;
		} ;
		gxTask.setResult(JsonUtil.toJsonString(trainResult));
		updateById(gxTask);
		return true;
	}

	@Override
	public Boolean getGxTasks(String token) {

		List<String> existTaskIds = gxTaskMapper.listTaskId();

		// 获取所有远程用户id列表
		List<RemoteUser> remoteUsers = remoteUserService.getGxUserList();

		for (RemoteUser remoteUser : remoteUsers) {
			String userToken = StringUtils.isNotEmpty(token) ? token : remoteUser.getThirdToken();
			List<JSONObject> userTasks = remoteTaskService.getTasks(remoteUser.getUserId(), SOFT_ID,
					userToken);
			for (JSONObject userTask : userTasks) {
				String taskId = userTask.getJSONObject("taskBaseData").getString("taskId");
				if (existTaskIds.contains(taskId)) {
					log.info(String.format("Skip existing task, taskId: %s", taskId));
					continue;
				}
				GxTaskContext gxTaskContext = getContextByData(userTask, token);

				// 共性任务数据未初始化完成
				if (gxTaskContext == null) {
					log.info(String.format("Fail to write task info, taskId: %s", taskId));
					return false;
				}

				create(gxTaskContext);
				log.info(String.format("Create task successfully, taskId: %s", taskId));
			}

		}
		return true;
	}

}
