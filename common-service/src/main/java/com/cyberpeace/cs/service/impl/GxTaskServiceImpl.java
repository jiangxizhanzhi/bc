package com.cyberpeace.cs.service.impl;

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
import com.cyberpeace.cs.dao.entity.GxOrgUsers;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.GxOrgUsersMapper;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.cs.service.context.GxTaskListContext;
import com.cyberpeace.cs.service.context.GxTaskListQuery;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GxTaskServiceImpl extends ServiceImpl<GxTaskMapper, GxTask> implements GxTaskService {

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@Autowired
	private GxOrgUsersMapper gxOrgUsersMapper;

	@Override
	public Boolean create(GxTaskContext gxTaskContext) {

		// 校验任务id是否重复
		if (gxTaskMapper.getByTaskId(gxTaskContext.getTaskId()) != null) {
			throw new BusinessException(ErrorCode.DUPLICATE_KEY);
		}

		GxTask task = BeanUtils.copy(gxTaskContext, GxTask.class);

		List<JSONObject> traineeList = JsonUtil.toJsonObjectArray(gxTaskContext.getTrainees(),
				JSONObject.class);

		Integer traineesCount = traineeList == null ? 0 : traineeList.size();

		task.setTraineesCount(traineesCount);

		task.setTrainees(gxTaskContext.getTrainees());

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

}
