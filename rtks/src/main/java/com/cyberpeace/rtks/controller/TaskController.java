package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.constant.TrialButtonConstant;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.rtks.controller.req.TaskAddReq;
import com.cyberpeace.rtks.dao.entity.Task;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.service.TaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-任务管理")
@RestController
@RequestMapping("/rtks")
@RequiresAuthentication
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("查询单条任务")
	@GetMapping("/task")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<Task> getTask(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		Task task = taskService.getById(resource_id);

		return new BaseRsp(task);
	}

	@ApiOperation("新增单条任务")
	@PostMapping("/task")
	@PermissionOperation(TrialButtonConstant.CMS_RTKS_TASK)
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> addTask(@RequestBody @Validated TaskAddReq taskAddReq) {
		Task toAddTask = new Task();
		BeanUtils.copyProperties(taskAddReq, toAddTask);

		taskService.save(toAddTask);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.CREATE, toAddTask.getResourceId(), LogContent.CR_FORMAT.format(
						OperationType.CREATE, LogContent.TASK.getMsg(), toAddTask.getResourceId()));

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条任务")
	@PutMapping("/task")
	@PermissionOperation(TrialButtonConstant.CMS_RTKS_TASK)
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> modifyTask(@RequestBody @Validated TaskAddReq taskAddReq) {
		Task toUpdateTask = new Task();
		BeanUtils.copyProperties(taskAddReq, toUpdateTask);

		taskService.updateById(toUpdateTask);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.UPDATE, toUpdateTask.getResourceId(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.TASK.getMsg(),
						toUpdateTask.getResourceId()));

		return new BaseRsp<>();
	}

	@ApiOperation("删除任务")
	@DeleteMapping("/task")
	@PermissionOperation(TrialButtonConstant.CMS_RTKS_TASK)
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> deleteTask(@RequestBody @Validated DeleteReq deleteReq) {

		List<Task> taskList = taskService.listByIds(deleteReq.getIds());

		taskService.removeByIds(deleteReq.getIds());
		roleService.updateBatchByDeleteRelatedIds(deleteReq, "task");

		taskList.forEach(task -> {
			remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
					OperationType.DELETE, task.getResourceId(), LogContent.CR_FORMAT.format(
							OperationType.DELETE, LogContent.TASK.getMsg(), task.getResourceId()));
		});

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/task/list")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<Task>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<Task> data = taskService.page(pageReqBase);

		return new BaseRsp(data);
	}

}
