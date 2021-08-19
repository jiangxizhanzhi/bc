package com.cyberpeace.oj.rtks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.controller.req.RoleAddReq;
import com.cyberpeace.oj.rtks.controller.req.RoleRelatedAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.entity.Task;
import com.cyberpeace.oj.rtks.service.RoleRelatedService;
import com.cyberpeace.oj.rtks.service.RoleService;
import com.cyberpeace.oj.rtks.service.TaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "TRKS-岗位管理")
@RestController
@RequestMapping("/rtks/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleRelatedService roleRelatedService;

	@Autowired
	private TaskService taskService;

	@ApiOperation("新增岗位")
	@PostMapping()
	public BaseRsp<Role> addRole(@RequestBody @Validated RoleAddReq roleAddReq) {
		Role role = roleService.addRole(roleAddReq);
		return new BaseRsp<>(role);
	}

	@ApiOperation("修改岗位")
	@PutMapping()
	public BaseRsp<Role> modifyTask(@RequestBody @Validated RoleAddReq roleAddReq) {
		Role role = roleService.modifyRole(roleAddReq);
		return new BaseRsp<>(role);
	}

	@ApiOperation("删除岗位")
	@DeleteMapping()
	public BaseRsp<Role> deleteFrame(@RequestBody @Validated DeleteReq deleteReq) {
		roleService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

	@ApiOperation("根据岗位名的模糊列表查询")
	@GetMapping()
	public BaseRsp<ListRsp<Role>> searchRoles(ListReq<Role> listReq) {
		ListRsp<Role> data = roleService.searchRolesByName(listReq);
		return new BaseRsp<>(data);
	}

	@ApiOperation("批量删除岗位职责/知识点/职责/能力清单")
	@DeleteMapping("/related")
	public BaseRsp<String> batchDeleteRoleRelated(@RequestBody @Validated DeleteReq deleteReq) {
		roleRelatedService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

	@ApiOperation("新增岗位职责清单")
	@PostMapping("/related/task")
	public BaseRsp<String> addTaskNameList(@RequestBody @Validated RoleRelatedAddReq req) {
		List<String> taskIds = req.getRelated_ids();
		String roleId = req.getRole_id();
		roleService.ensureRoleExists(roleId);
		for (String taskId : taskIds) {
			taskService.ensureTaskExists(taskId);
		}
		roleRelatedService.addRoleRelated(roleId, RoleRelatedType.TASK, taskIds);
		return new BaseRsp<>();
	}

	@ApiOperation("获取岗位职责清单")
	@GetMapping("/related/task")
	public BaseRsp<List<Task>> getTaskNameList(String roleId) {
		List<String> taskIds = roleRelatedService.getRelatedIds(roleId, RoleRelatedType.TASK);
		List<Task> tasks = taskService.listByIds(taskIds);
		return new BaseRsp<>(tasks);
	}

	// todo
	// 编排岗位基础课
	// 编排岗位专业课
}
