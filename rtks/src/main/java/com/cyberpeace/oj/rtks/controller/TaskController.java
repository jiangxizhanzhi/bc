package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.TaskAddReq;
import com.cyberpeace.oj.rtks.controller.req.TaskListReq;
import com.cyberpeace.oj.rtks.dao.entity.Task;
import com.cyberpeace.oj.rtks.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "TRKS-任务管理")
@RestController
@RequestMapping("/rtks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@ApiOperation("查询单条任务")
	@GetMapping("/task")
	public BaseRsp<Task> get1(
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

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/task/list")
	public BaseRsp<List<Task>> list(@Validated TaskListReq taskListReq) {
		ListRsp<Task> data = taskService.page(taskListReq);

		return new BaseRsp(data);
	}

}
