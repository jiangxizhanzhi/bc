package com.cyberpeace.rtks.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.controller.req.UserRoleAddReq;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.UserRelateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-用户绑定岗位以及相关api")
@RestController
@RequestMapping("/relate/user")
@RequiresAuthentication
@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER },
		logical = Logical.OR)
public class UserRelateController {

	@Autowired
	private UserRelateService userRelateService;

	@ApiOperation("用户新增或修改岗位")
	@PostMapping("/role")
	@RequiresPermissions(MenuConstant.WEB_OJ_COURSE)
	public BaseRsp<UserRole>
			relateUser(@ApiParam(name = "req", value = "岗位信息") @RequestBody UserRoleAddReq req) {

		String userId = UserInfoContext.getUserContext().getUserId();
		String userName = UserInfoContext.getUserContext().getUserName();
		UserRole userRole = userRelateService.roleRelateUser(req.getRoleId(), userId, userName);
		BaseRsp<UserRole> rsp = new BaseRsp<>();
		rsp.setData(userRole);
		return rsp;
	}

	@ApiOperation("查询用户和岗位信息")
	@GetMapping("/role/info")
	public BaseRsp<UserRole> getUserAndRoleInfo() {
		UserRole userRole = userRelateService
				.getUserRoleInfo(UserInfoContext.getUserContext().getUserId());
		BaseRsp<UserRole> rsp = new BaseRsp<>();
		rsp.setData(userRole);
		return rsp;
	}
}
