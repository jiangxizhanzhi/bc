package com.cyberpeace.rtks.controller;

import org.apache.commons.lang3.StringUtils;
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
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.UserRelateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户与Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "RTKS-用户绑定岗位以及相关api")
@RestController
@RequestMapping("/relate/user")
@Slf4j
@RequiresAuthentication
public class UserRelateController {

	/**
	 * 用户与Service
	 *
	 * @see UserRelateService
	 */
	@Autowired
	private UserRelateService userRelateService;

	/**
	 * RTKS态势Service
	 *
	 * @see RtksSituationService
	 */
	@Autowired
	private RtksSituationService rtksSituationService;

	/**
	 * 关联用户
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<UserRole> }
	 * @throws
	 * @see UserRoleAddReq
	 * @see BaseRsp<UserRole>
	 */
	@ApiOperation("用户新增或修改岗位")
	@PostMapping("/role")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<UserRole>
			relateUser(@ApiParam(name = "req", value = "岗位信息") @RequestBody UserRoleAddReq req) {

		String userId = UserInfoContext.getUserContext().getUserId();
		String userName = UserInfoContext.getUserContext().getUserName();
		// 用户切换岗位前记录当前岗位匹配度, 非事务，不阻塞
		String oldRoleId = UserInfoContext.getUserContext().getRoleId();
		try {
			if (StringUtils.isNotBlank(oldRoleId)) {
				rtksSituationService.recordSituation(userId, userName, oldRoleId,
						UserInfoContext.getUserContext().getRoleName());
			}
		} catch (Exception e) {
			log.info(String.format("failed to record rtks match with userId:[%s], roleId: [%s]",
					userId, oldRoleId), e);
		}
		// 切换岗位
		UserRole userRole = userRelateService.roleRelateUser(req.getRoleId(), userId, userName);
		BaseRsp<UserRole> rsp = new BaseRsp<>();
		rsp.setData(userRole);
		return rsp;
	}

	/**
	 * 获取用户和岗位信息
	 *
	 * @return {@code BaseRsp<UserRole> }
	 * @throws
	 * @see BaseRsp<UserRole>
	 */
	@ApiOperation("查询用户和岗位信息")
	@GetMapping("/role/info")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<UserRole> getUserAndRoleInfo() {
		UserRole userRole = userRelateService
				.getUserRoleInfo(UserInfoContext.getUserContext().getUserId());
		BaseRsp<UserRole> rsp = new BaseRsp<>();
		rsp.setData(userRole);
		return rsp;
	}
}
