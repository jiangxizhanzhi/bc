package com.cyberpeace.oj.ojbase.controller;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.cyberpeace.common.util.ObjUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

public abstract class PublicController<T extends IService<E>, E> {

	@Autowired
	protected T baseService;

	protected Class<E> entityClass = currentModelCls();

	@SuppressWarnings("unchecked")
	private Class<E> currentModelCls() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
	}

	@PatchMapping("/batch-public")
	@ApiOperation("资源公开/隐藏")
	@RequiresPermissions(
			value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_EXERCISES,
					MenuConstant.CMS_THEORY_PRACTICE, MenuConstant.CMS_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<Boolean> batchPublic(@RequestBody @Validated ResourcePublicReq requestData) {

		Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
			AuthAndPublicController.AuthAndPublicData data = new AuthAndPublicController.AuthAndPublicData();
			data.setIsPublic(requestData.getIsPublic());
			data.setModifyUserId(UserInfoContext.getUserContext().getUserId());
			data.setResourceId(resourceId);
			return data;
		});

		return new BaseRsp<>(result);
	}

	/**
	 * 数据更新
	 * @param collection
	 * @param mapperValue
	 * @param <String>
	 * @param <V>
	 * @return
	 */
	protected <String, V> Boolean updateData(Collection<String> collection,
			Function<String, V> mapperValue) {
		List<V> publicDatas = StreamApiUtils.map(collection, mapperValue);
		List<E> entityList = BeanUtils.deepCopyList(publicDatas, entityClass);
		boolean result = true;
		for (E entity : entityList) {
			Field field = null;
			Boolean builtin = null;
			java.lang.String createUserId = "";
			try {
				java.lang.String resourceId = ObjUtil.getCertainFieldValue(entity, "resourceId");
//				field = entity.getClass().getDeclaredField("resourceId");
//				// 如果该属性是私有的，需要将其设为可访问状态
//				field.setAccessible(true);
//				java.lang.String resourceId = (java.lang.String) field.get(entity);
				E entityData = baseService.getById(resourceId);
				createUserId = ObjUtil.getCertainFieldValue(entityData, "createUserId");
//				field = entity.getClass().getDeclaredField("builtin");
				// 如果该属性是私有的，需要将其设为可访问状态
//				field.setAccessible(true);
//				builtin = (Boolean) field.get(entityData);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 判断资源是否内置，允许修改
			AuthContextUtils.validateBuiltin(!createUserId.equals(UserInfoContext.getUserContext().getUserId()));

			result &= baseService.updateById(entity);

		}
		return result;
	}

	/**
	 * 数据中间存储
	 */
	@Data
	class AuthAndPublicData {

		private String resourceId;

		private Boolean isPublic;

		private Integer readAuthType;

		private String readAuthIds;

		private Integer operationAuthType;

		private String operationAuthIds;

		private String modifyUserId;
	}

}
