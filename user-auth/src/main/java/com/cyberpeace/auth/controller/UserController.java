package com.cyberpeace.auth.controller;

import com.cyberpeace.auth.controller.req.UserImportReq;
import com.cyberpeace.auth.controller.req.UserReq;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.*;
import com.cyberpeace.common.annoations.AdminRequired;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.IdListReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.MultiSelectReq;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cs.dao.entity.DictInfo;
import com.cyberpeace.cs.service.DictInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(value = "用户相关", tags = "用户API")
public class UserController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private DictInfoService dictInfoService;

	@ApiOperation("用户更新密码")
	@PostMapping("/users/resetpwd/self/")
	public BaseRsp resetPwdSelf(@RequestBody ResetPwdSelfReq req) {
		sysUserService.resetPwdself(req);
		// logout
		return new BaseRsp<>();
	}

	@ApiOperation("管理员重置密码")
	@PostMapping("/users/resetpwd/")
	@AdminRequired
	public BaseRsp resetPwd(@RequestBody ResetPwdReq req) {
		sysUserService.resetPwd(req);
		// logout
		return new BaseRsp<>();
	}

	@ApiOperation("获取当前用户信息")
	@GetMapping("/current/user/info")
	public BaseRsp<SysUser> getCurrentUserInfo() {
		SysUser userInfo = UserInfoContext.getNewUserContext();
		if (userInfo != null) {
			// 不能返回密码信息
			userInfo.setPassword("");
		}
		return new BaseRsp<>(userInfo);
	}

	@ApiOperation("创建用户")
	@PostMapping("/user/")
	@AdminRequired
	public BaseRsp createUser(@RequestBody UserAddReq req) {
		req.validate();
		sysUserService.createUser(req);
		return new BaseRsp();
	}

	@ApiOperation("删除用户")
	@DeleteMapping("/user/")
	@AdminRequired
	public BaseRsp deleteUser(@RequestBody IdListReq req) {
		sysUserService.deleteUser(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("编辑用户")
	@PatchMapping("/user/")
	public BaseRsp updateUser(@RequestBody UserModifyReq req) {
		sysUserService.updateUser(req);
		return new BaseRsp();
	}

	@ApiOperation("用户详情")
	@GetMapping("/user/")
	public BaseRsp<UserRes>
			userDetail(@ApiParam(value = "用户id", name = "resource_id") String resource_id) {
		UserRes res = sysUserService.getUserDetailById(resource_id);
		return new BaseRsp(res);
	}

	@ApiOperation("用户分页列表")
	@GetMapping("/user/list")
	public BaseRsp<ListRsp<UserRes>> pageUser(UserReq req) {
		ListRsp<UserRes> rsp = sysUserService.pageUser(req);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("更新用户头像")
	@GetMapping("/update/avatar")
	public BaseRsp updateAvatar(@ApiParam(name = "avatar", value = "头像路径") String avatar,
						  @ApiParam(name = "resource_id", value = "用户id") @RequestParam("resource_id") String resourceId) {
		sysUserService.updateAvatar(avatar, resourceId);
		return new BaseRsp<>();
	}

	@ApiOperation("默认头像列表")
	@GetMapping("/avatar/list")
	public BaseRsp<List<DictInfo>> getDefaultAvatar() {
		List<DictInfo> dictInfo = dictInfoService.getDictInfo(DictInfo.KeyModel.AVATAR.getKey());
		return new BaseRsp<>(dictInfo);
	}

	@ApiOperation("更新用户状态")
	@PostMapping("/enable")
	public BaseRsp updateUserEnable(@RequestBody UserEnableReq req) {
		sysUserService.updateUserEnable(req);
		return new BaseRsp();
	}

	@ApiOperation("用户导入")
	@PostMapping("/import")
	public BaseRsp importUser(UserImportReq req){

		return sysUserService.importUser(req);
	}
}
