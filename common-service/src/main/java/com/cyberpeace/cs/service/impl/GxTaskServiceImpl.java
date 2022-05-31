package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.GxOrgUsers;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.GxOrgUsersMapper;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.cs.service.context.GxTaskListContext;
import com.cyberpeace.cs.service.context.GxTaskListQuery;
import com.cyberpeace.integratation.service.RemoteTaskService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GxTaskServiceImpl extends ServiceImpl<GxTaskMapper, GxTask> implements GxTaskService {

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@Autowired
	private GxOrgUsersMapper gxOrgUsersMapper;

	@Autowired
	private RemoteTaskService remoteTaskService;

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

		GxOrgUsers gxOrgUsers = new GxOrgUsers();
		gxOrgUsers.setClassRoomId(classRoomId);
		gxOrgUsers.setUserIds(JsonUtil.toJsonString(userList));
		gxOrgUsers.setOrgName(orgName);
		gxOrgUsersMapper.insert(gxOrgUsers);

		return gxOrgUsers.getResourceId();
	}

	@Override
	public List<String> getLocalUserList(GxOrgUsers gxOrgUsers) {
		List<String> userList = Lists.newArrayList();
		if (StringUtils.isNotEmpty(gxOrgUsers.getUserIds())) {
			userList = JsonUtil.toJsonObjectArray(gxOrgUsers.getUserIds(), String.class);
		}
		return userList;
	}

	@Override
	public Boolean clearClassRoomOrg(String classRoomId) {

		List<String> orgs = gxOrgUsersMapper.getByClassRoomId(classRoomId);

		if (orgs != null && !orgs.isEmpty()) {
			gxOrgUsersMapper.deleteBatchIds(orgs);
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

}
