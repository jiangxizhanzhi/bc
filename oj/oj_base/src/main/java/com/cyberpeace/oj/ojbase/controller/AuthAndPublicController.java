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
/**
 * 这段代码定义了一个抽象控制器类 `AuthAndPublicController`，它继承自 `PublicController`。该类主要用于处理与资源认证和共享相关的请求。以下是详细的分析： 
 * ### 类和方法概述 
 * 1. **类定义** 
 * - `AuthAndPublicController<T extends IService<E>, E>` 是一个泛型抽象类，继承自 `PublicController`。 
 * - 使用了 Lombok 的 `@Slf4j` 注解来简化日志记录。 
 * 2. **方法** 
 * - `batchAuth(ResourceAuthReq requestData)`：批量认证资源。接收 `ResourceAuthReq` 对象作为请求体，并返回 `BaseRsp<Boolean>`。 
 * - `getAuth(String resourceId)`：获取资源的认证信息。接收资源ID作为查询参数，并返回 `BaseRsp<ReadAuthInfo>`。 
 * - `getOperation(String resourceId)`：获取资源的操作权限信息。接收资源ID作为查询参数，并返回 `BaseRsp<OperationAuthInfo>`。 
 * - `batchShare(ResourceShareReq requestData)`：批量共享资源。接收 `ResourceShareReq` 对象作为请求体，并返回 `BaseRsp<Boolean>`。 
 * ### 主要功能 
 * - **批量认证 (`batchAuth` 方法)**： 
 * - 更新多个资源的读取权限。 
 * - 使用 `updateData` 方法批量更新资源的认证信息。 
 * - 返回一个包含结果的 `BaseRsp<Boolean>` 对象。 
 * - **获取认证信息 (`getAuth` 方法)**： 
 * - 根据资源ID获取资源的读取权限信息。 
 * - 返回一个包含认证信息的 `BaseRsp<ReadAuthInfo>` 对象。 
 * - **获取操作权限信息 (`getOperation` 方法)**： 
 * - 根据资源ID获取资源的操作权限信息。 
 * - 如果操作权限ID列表不为空，则通过 `RemoteUserService` 获取用户信息并映射到 `OperationUserInfo` 列表。 
 * - 返回一个包含操作权限信息的 `BaseRsp<OperationAuthInfo>` 对象。 
 * - **批量共享 (`batchShare` 方法)**： 
 * - 更新多个资源的操作权限。 
 * - 验证操作类型是否有效。 
 * - 使用 `updateData` 方法批量更新资源的操作权限信息。 
 * - 返回一个包含结果的 `BaseRsp<Boolean>` 对象。 
 * ### 技术细节 
 * - **依赖注入**： 
 * - 使用 `SpringContextUtil.getBeanByClass` 方法获取 `RemoteUserService` 实例。 
 * - **数据转换**： 
 * - 使用 `BeanUtils.copy` 和 `JsonUtil` 进行对象转换和 JSON 处理。 
 * - **权限控制**： 
 * - 使用 `@RequiresPermissions` 注解进行权限验证，确保只有具有特定权限的用户才能访问这些接口。 
 * ### 总结 
 * `AuthAndPublicController` 是一个处理资源认证和共享的抽象控制器类。它提供了四个主要的RESTful API 方法，用于批量认证、获取认证信息、获取操作权限信息和批量共享资源。这些方法使用泛型和依赖注入来处理不同类型的资源和服务调用，并通过 `BaseRsp` 对象返回统一的结果格式。 
**/
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

