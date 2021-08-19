package com.cyberpeace.oj.rtks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil2;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.controller.req.RoleAddReq;
import com.cyberpeace.oj.rtks.controller.req.RoleRelatedAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Ability;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.entity.Task;
import com.cyberpeace.oj.rtks.service.*;

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

	@Autowired
	private AbilityService abilityService;

	@Autowired
	private LevelService levelService;

	@ApiOperation("新增岗位")
	@PostMapping()
	public BaseRsp<Role> addRole(@RequestBody @Validated RoleAddReq roleAddReq) {
		roleAddReq.setResourceId(null);

		levelService.ensureLevelIdValid(roleAddReq.getLevelId());

		Role role = new Role();
		BeanUtils.copyProperties(roleAddReq, role);

		roleService.save(role);

		return new BaseRsp<>(role);
	}

	@ApiOperation("修改岗位")
	@PutMapping()
	public BaseRsp<Role> modifyTask(@RequestBody @Validated RoleAddReq roleAddReq) {
		levelService.ensureLevelIdValid(roleAddReq.getLevelId());

		Role role = new Role();
		BeanUtils.copyProperties(roleAddReq, role);

		roleService.updateById(role);

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
		IPage<Role> iPage = roleService.searchRolesByName(listReq.toPageParam(),
				listReq.getKeyword());
		ListRsp<Role> listRsp = PageUtil2.getListResult(iPage);
		return new BaseRsp<>(listRsp);
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
		return addNameList(req, taskService, RoleRelatedType.TASK);
	}

	@ApiOperation("获取岗位职责清单")
	@GetMapping("/related/task")
	public BaseRsp<List<Task>> getTaskNameList(String role_id) {
		return getNameList(role_id, taskService, RoleRelatedType.TASK);
	}

	// @ApiOperation("新增知识清单")
	// @PostMapping("/related/knowledge")
	// public BaseRsp<String> addKnowledgeNameList(@RequestBody @Validated
	// RoleRelatedAddReq req) {
	// return addNameList(req, taskService, RoleRelatedType.KNOWLEDGE);
	// }
	//
	// @ApiOperation("获取知识清单")
	// @GetMapping("/related/knowledge")
	// public BaseRsp<List<Task>> getKnowledgeNameList(String role_id) {
	// return getNameList(role_id, taskService, RoleRelatedType.KNOWLEDGE);
	// }
	//
	// @ApiOperation("新增技能清单")
	// @PostMapping("/related/skill")
	// public BaseRsp<String> addSkillNameList(@RequestBody @Validated
	// RoleRelatedAddReq req) {
	// return addNameList(req, taskService, RoleRelatedType.SKILL);
	// }
	//
	// @ApiOperation("获取技能清单")
	// @GetMapping("/related/skill")
	// public BaseRsp<List<Task>> getSkillNameList(String role_id) {
	// return getNameList(role_id, taskService, RoleRelatedType.SKILL);
	// }

	@ApiOperation("新增能力清单")
	@PostMapping("/related/ability")
	public BaseRsp<String> addAbilityNameList(@RequestBody @Validated RoleRelatedAddReq req) {
		return addNameList(req, abilityService, RoleRelatedType.ABILITY);
	}

	@ApiOperation("获取能力清单")
	@GetMapping("/related/ability")
	public BaseRsp<List<Ability>> getAbilityNameList(String role_id) {
		return getNameList(role_id, abilityService, RoleRelatedType.ABILITY);
	}

	private <T> BaseRsp<String> addNameList(RoleRelatedAddReq req, IService<T> service, int type) {
		List<String> relatedIds = req.getRelated_ids();
		String roleId = req.getRole_id();

		roleService.ensureRoleExists(roleId);
		for (String taskId : relatedIds) {
			T relatedObject = service.getById(taskId);
			if (relatedObject == null) {
				throw new ResourceNotFoundException(taskId + "未找到");
			}
		}

		roleRelatedService.addRoleRelated(roleId, type, relatedIds);

		return new BaseRsp<>();
	}

	private <T> BaseRsp<List<T>> getNameList(String role_id, IService<T> service, int type) {
		List<String> relatedIds = roleRelatedService.getRelatedIds(role_id, type);

		List<T> relatedObjects;
		if (relatedIds.size() == 0) {
			relatedObjects = new ArrayList<>();
		} else {
			relatedObjects = service.listByIds(relatedIds);
		}

		return new BaseRsp<>(relatedObjects);
	}

	private interface RoleRelatedType {

		int TASK = 1;

		int KNOWLEDGE = 2;

		int SKILL = 3;

		int ABILITY = 4;
	}

}
