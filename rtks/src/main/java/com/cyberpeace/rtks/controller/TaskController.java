package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.common.constant.MenuConstant;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
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
@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private RoleService roleService;

	@ApiOperation("查询单条任务")
	@GetMapping("/task")
	public BaseRsp<Task> getTask(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		Task task = taskService.getById(resource_id);

		return new BaseRsp(task);
	}

	@ApiOperation("新增单条任务")
	@PostMapping("/task")
	public BaseRsp<String> addTask(@RequestBody @Validated TaskAddReq taskAddReq) {
		Task toAddTask = new Task();
		BeanUtils.copyProperties(taskAddReq, toAddTask);

		taskService.save(toAddTask);

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条任务")
	@PutMapping("/task")
	public BaseRsp<String> modifyTask(@RequestBody @Validated TaskAddReq taskAddReq) {
		Task toUpdateTask = new Task();
		BeanUtils.copyProperties(taskAddReq, toUpdateTask);

		taskService.updateById(toUpdateTask);

		return new BaseRsp<>();
	}

	@ApiOperation("删除任务")
	@DeleteMapping("/task")
	public BaseRsp<String> deleteTask(@RequestBody @Validated DeleteReq deleteReq) {
		taskService.removeByIds(deleteReq.getIds());
		roleService.updateBatchByDeleteRelatedIds(deleteReq, "task");

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/task/list")
	public BaseRsp<List<Task>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<Task> data = taskService.page(pageReqBase);

		return new BaseRsp(data);
	}

}
