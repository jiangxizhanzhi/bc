package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.TimeUtil;
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
import com.cyberpeace.integratation.service.RemoteTaskService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

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

	@Override
	public Boolean create(GxTaskContext gxTaskContext) {

		// 校验任务id是否重复
		if (gxTaskMapper.getByTaskId(gxTaskContext.getTaskId()) != null) {
			throw new BusinessException(ErrorCode.DUPLICATE_KEY);
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

		// gxTaskContext.setTraineesCount(taskBaseData.getInteger("numberOfTrainees"));
		// gxTaskContext
		// .setTrainees(JsonUtil.toJsonString(taskDetails.getJSONArray("trainPlanPersons")));

		// 处理任务用户数据
		setTrainees(gxTaskContext, taskId, token);

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

	private void setTrainees(GxTaskContext gxTaskContext, String taskId, String token) {

		String trainees = remoteTaskService.getTaskUsers(taskId, token);
		List<JSONObject> traineeList = JsonUtil.toJsonObjectArray(trainees, JSONObject.class);
		Integer traineesCount = traineeList == null ? 0 : traineeList.size();
		gxTaskContext.setTrainees(trainees);
		gxTaskContext.setTraineesCount(traineesCount);

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
		String token = UserInfoContext.getUserContext().getThirdToken();

		// token不存在时报错 todo

		ClassroomScore classroomScore = classroomScoreMapper.selectByLessonIdAndUserId(lessonId,
				userId);

		// 未找到记录时直接报错
		if (classroomScore == null) {
			return false;
		}

		Boolean stopResult = remoteTaskService.stopClassroom(classroomScore.getTaskId(), userId,
				token);

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
		classroomScores.forEach(classroomScore -> {
			trainScores.add(new TrainScore(classroomScore.getUserId(), classroomScore.getScore()));
		});

		TrainResult trainResult = new TrainResult(gxTask.getDuration(), gxTask.getTaskId(),
				trainScores);

		return trainResult;
	}

	@Override
	public Boolean finishClassroomTask(GxTask gxTask, String userId, String token) {
		TrainResult trainResult = getTrainResult(gxTask, userId);
		remoteTaskService.stopClassroomTask(trainResult, token);
		return true;
	}

}
