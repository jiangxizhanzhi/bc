package com.cyberpeace.oj.ojbase.controller;

import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.controller.req.ResourceAuthReq;
import com.cyberpeace.oj.ojbase.controller.req.ResourceShareReq;
import com.cyberpeace.oj.ojbase.controller.res.OperationAuthInfo;
import com.cyberpeace.oj.ojbase.controller.res.OperationUserInfo;
import com.cyberpeace.oj.ojbase.controller.res.ReadAuthInfo;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 认证和公共Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PublicController
 * @since 1.0.0
 */
@Slf4j
public abstract class AuthAndPublicController<T extends IService<E>, E> extends PublicController {

	/**
	 * 批量认证
	 *
	 * @param requestData 请求数据
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ResourceAuthReq
	 * @see BaseRsp<Boolean>
	 */
	@PatchMapping("/batch-auth")
	@ApiOperation("资源授权访问")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_EXERCISES },
			logical = Logical.OR)
	public BaseRsp<Boolean> batchAuth(@RequestBody @Validated ResourceAuthReq requestData) {

		Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
			AuthAndPublicData data = new AuthAndPublicData();
			data.setResourceId((String) resourceId);
			data.setReadAuthType(requestData.getReadAuthType());

			data.setReadAuthIds(JsonUtil.arrayToString(requestData.getReadAuthIds()));

			return data;
		});

		return new BaseRsp<>(result);
	}

	/**
	 * 获取认证
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ReadAuthInfo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<ReadAuthInfo>
	 */
	@GetMapping("/get_auth")
	@ApiOperation("获取资源的授权访问信息")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_EXERCISES },
			logical = Logical.OR)
	public BaseRsp<ReadAuthInfo> getAuth(@ApiParam(name = "resource_id",
			value = "资源编号") @RequestParam("resource_id") String resourceId) {

		E entity = (E) baseService.getById(resourceId);
		AuthTable publicData = BeanUtils.copy(entity, AuthTable.class);

		ReadAuthInfo authInfo = new ReadAuthInfo();
		authInfo.setReadAuthType(publicData.getReadAuthType());
		authInfo.setReadAuthIds(
				JsonUtil.toJsonObjectArray(publicData.getReadAuthIds(), String.class));

		return new BaseRsp<>(authInfo);
	}

	/**
	 * 获取实操
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<OperationAuthInfo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<OperationAuthInfo>
	 */
	@GetMapping("/get_share")
	@ApiOperation("获取资源的权限操作信息")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_EXERCISES },
			logical = Logical.OR)
	public BaseRsp<OperationAuthInfo> getOperation(@ApiParam(name = "resource_id",
			value = "资源编号") @RequestParam("resource_id") String resourceId) {

		E entity = (E) baseService.getById(resourceId);
		AuthTable publicData = BeanUtils.copy(entity, AuthTable.class);

		OperationAuthInfo authInfo = new OperationAuthInfo();
		authInfo.setOperationAuthType(publicData.getOperationAuthType());

		List<String> userIds = JsonUtil.toJsonObjectArray(publicData.getOperationAuthIds(),
				String.class);
		if (CollectionUtils.isNotEmpty(userIds)) {
			RemoteUserService userService = SpringContextUtil
					.getBeanByClass(RemoteUserService.class);

			List<UserInfo> userInfos = userService.getByIdList(userIds);
			List<OperationUserInfo> operationUserInfos = StreamApiUtils.map(userInfos, userInfo -> {
				OperationUserInfo readUserInfo = BeanUtils.copy(userInfo, OperationUserInfo.class);
				readUserInfo.setId(userInfo.getUserId());
				readUserInfo.setUsername(userInfo.getUserName());
				return readUserInfo;
			});

			authInfo.setOperationAuthIds(operationUserInfos);
		} else {
			authInfo.setOperationAuthIds(Lists.newArrayList());
		}

		return new BaseRsp<>(authInfo);

	}

	/**
	 * 批量共享
	 *
	 * @param requestData 请求数据
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ResourceShareReq
	 * @see BaseRsp<Boolean>
	 */
	@PatchMapping("/batch-share")
	@ApiOperation("资源授权操作")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_EXERCISES },
			logical = Logical.OR)
	public BaseRsp<Boolean> batchShare(@RequestBody @Validated ResourceShareReq requestData) {

		List<Integer> listCode = EnumUtils.getListCode(AuthTable.AuthType.class);
		if (!listCode.contains(requestData.getOperationAuthType())) {
			log.error("type doesn't contains [{}]", requestData.getOperationAuthType());
			throw new BusinessException(OjBusinessErrorCode.TYPE_ERROR,requestData.getOperationAuthType());
		}
		Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
			AuthAndPublicData data = new AuthAndPublicData();
			data.setResourceId((String) resourceId);
			data.setOperationAuthType(requestData.getOperationAuthType());
			data.setOperationAuthIds(JsonUtil.arrayToString(requestData.getOperationAuthIds()));
			return data;
		});
		return new BaseRsp<>(result);
	}

}
