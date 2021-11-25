package com.cyberpeace.oj.ojbase.controller;

import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.controller.res.OperationUserInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.ojbase.controller.req.ResourceAuthReq;
import com.cyberpeace.oj.ojbase.controller.req.ResourceShareReq;
import com.cyberpeace.oj.ojbase.controller.res.OperationAuthInfo;
import com.cyberpeace.oj.ojbase.controller.res.ReadAuthInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

public abstract class AuthAndPublicController<T extends IService<E>, E> extends PublicController {

	@PatchMapping("/batch-auth")
	@ApiOperation("资源授权访问")
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

	@GetMapping("/get_auth")
	@ApiOperation("获取资源的授权访问信息")
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

	@GetMapping("/get_share")
	@ApiOperation("获取资源的权限操作信息")
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
			List<OperationUserInfo> operationUserInfos = StreamApiUtils.simpleMap(userInfos,
					userInfo -> {
						OperationUserInfo readUserInfo = BeanUtils.copy(userInfo,
								OperationUserInfo.class);
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

	@PatchMapping("/batch-share")
	@ApiOperation("资源授权操作")
	public BaseRsp<Boolean> batchShare(@RequestBody ResourceShareReq requestData) {

		Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
			AuthAndPublicData data = new AuthAndPublicData();
			data.setResourceId((String) resourceId);
			data.setOperationAuthType(requestData.getOperationAuthType());
			if (CollectionUtils.isNotEmpty(requestData.getOperationAuthIds())) {
				data.setOperationAuthIds(JsonUtil.arrayToString(requestData.getOperationAuthIds()));
			}
			return data;
		});
		return new BaseRsp<>(result);
	}

}
