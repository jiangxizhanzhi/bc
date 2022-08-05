package com.cyberpeace.cs.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.req.task.TaskStopReq;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "共性平台任务接口-无需认证")
@RestController
@RequestMapping("/unauth/task")
@Slf4j
public class GxUnAuthTaskController {

	@Autowired
	private GxTaskService gxTaskService;

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@ApiOperation("上报任务结束")
	@PostMapping("/stop")
	public BaseRsp<Boolean> stopTask(@Validated TaskStopReq taskStopReq) {
		GxTask gxTask = gxTaskMapper.getByTaskId(taskStopReq.getTaskId());

		if (gxTask == null) {
			throw new BusinessException(ErrorCode.TASK_NOT_FOUND);
		}

		if (StringUtils.isNotEmpty(gxTask.getResult())) {
			throw new BusinessException(ErrorCode.TASK_IS_FINISHED);
		}

		gxTask.setResult(taskStopReq.getResult());
		gxTaskService.updateById(gxTask);
		return new BaseRsp<>(true);
	}

}
