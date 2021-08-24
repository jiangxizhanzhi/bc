package com.cyberpeace.oj.rtks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil2;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.controller.role.*;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "TRKS-岗位管理")
@RestController
@RequestMapping("/rtks/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private RolePorter rolePorter;

	@ApiOperation("新增岗位")
	@PostMapping()
	public BaseRsp<RoleDetailOutput> create(@RequestBody @Validated RoleAddInput input) {
		Role role = rolePorter.getRoleFromInput(input, false);

		roleService.save(role);

		return retrieve(role.getResourceId());
	}

	@ApiOperation("修改岗位")
	@PutMapping()
	public BaseRsp<RoleDetailOutput> update(@RequestBody @Validated RoleModifyInput input) {
		String resourceId = input.getResourceId();

		roleService.getOr404(resourceId);

		Role role = rolePorter.getRoleFromInput(input, true);

		roleService.updateById(role);

		return retrieve(resourceId);
	}

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<RoleDetailOutput> retrieve(String resource_id) {
		Role role = roleService.getOr404(resource_id);
		return new BaseRsp<>(rolePorter.getDetailOutputFromRole(role));
	}

	@ApiOperation("删除岗位")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		roleService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

	@ApiOperation("根据岗位名的模糊列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<RoleOutput>> list(ListReq<Role> listReq) {
		IPage<Role> page = roleService.searchRolesByName(listReq.toPageParam(),
				listReq.getKeyword());
		return PageUtil2.getListResult(page, rolePorter::getBasicOutputFromRole);
	}

	// todo 相关课程编排 修改技能/知识时课程的联动
	// todo 层级和岗位作为子节点时互斥
}
