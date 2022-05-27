package com.cyberpeace.cs.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cs.controller.req.task.TaskBindReq;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.cs.service.context.*;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "共性平台任务接口")
@RestController
@RequestMapping("/common/task")
@RequiresAuthentication
@Slf4j
public class GxTaskController {

	@Autowired
	private GxTaskService gxTaskService;

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<Boolean> create(@RequestBody @Validated GxTaskContext gxTaskContext) {
		Boolean result = gxTaskService.create(gxTaskContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("获取任务列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<GxTaskListContext>> getTaskList(@Validated GxTaskListQuery query) {

		IPage<GxTaskListContext> data = gxTaskService.list(query);
		for (GxTaskListContext gxTaskListContext : data.getRecords()) {
			gxTaskListContext.setTaskStatus();
			gxTaskListContext.setUserIds(gxTaskListContext.getTrainees());
		}
		return new BaseRsp<>(PageUtil.getListResult(data));
	}

	@ApiOperation("获取任务的用户列表")
	@GetMapping("/user_list")
	public BaseRsp<ListRsp<TaskUserContext>> getUserList(@Validated GxTaskUserListQuery query) {
		ListRsp<TaskUserContext> rsp = PageUtil.getNullResult();
		GxTask gxTask = gxTaskMapper.getByTaskId(query.getTaskId());

		List<JSONObject> userList = JsonUtil.toJsonObjectArray(gxTask.getTrainees(),
				JSONObject.class);
		List<TaskUserContext> users = Lists.newArrayList();
		for (JSONObject user : userList) {
			TaskUserContext taskUserContext = new TaskUserContext();
			taskUserContext.setUserName(user.getString("userName"));
			taskUserContext.setOrgName(user.getJSONObject("userDetailDTO").getString("orgName"));
			taskUserContext.setName(user.getString("chineseName"));
			users.add(taskUserContext);
		}

		rsp = PageUtil.getListResult(users, query.toPageParam(), users.size());
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("绑定任务与事件")
	@PostMapping("/bind_task")
	public BaseRsp<Boolean> bindTask(@Validated TaskBindReq taskBindReq) {
		GxTask gxTask = gxTaskMapper.getByTaskId(taskBindReq.getTaskId());
		gxTask.setEventId(taskBindReq.getEventId());
		gxTask.setEventType(taskBindReq.getEventType());
		gxTaskService.updateById(gxTask);
		return new BaseRsp<>(true);
	}

}
