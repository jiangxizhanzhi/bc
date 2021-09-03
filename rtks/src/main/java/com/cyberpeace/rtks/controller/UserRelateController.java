package com.cyberpeace.rtks.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.UserRelateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-用户绑定岗位以及相关api")
@RestController
@RequestMapping("/relate/user")
@RequiresAuthentication
public class UserRelateController {

	@Autowired
	private UserRelateService userRelateService;

	@ApiOperation("用户新增或修改岗位")
	@PostMapping("/role")
	public BaseRsp<UserRole>
			relateUser(@ApiParam(name = "role_id") @RequestParam("role_id") String roleId) {

		String userId = UserInfoContext.getUserContext().getUserId();
		String userName = UserInfoContext.getUserContext().getUserName();
		UserRole userRole = userRelateService.roleRelateUser(roleId, userId, userName);
		BaseRsp<UserRole> rsp = new BaseRsp<>();
		rsp.setData(userRole);
		return rsp;
	}

	@ApiOperation("查询用户和岗位信息")
	@GetMapping("/role/info")
	public BaseRsp<UserRole> getUserAndRoleInfo() {
		UserRole userRole = userRelateService.getUserRoleInfo(UserInfoContext.getUserContext().getUserId());
		BaseRsp<UserRole> rsp = new BaseRsp<>();
		rsp.setData(userRole);
		return rsp;
	}
}
