package com.cyberpeace.oj.ojbase.controller;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

/**
 * 公共Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public abstract class PublicController<T extends IService<E>, E> {

	/**
	 * 基础Service
	 *
	 * @see T
	 */
	@Autowired
	protected T baseService;

	/**
	 * 实体类
	 *
	 * @see Class<E>
	 */
	protected Class<E> entityClass = currentModelCls();

	/**
	 * 当前型号 CLS
	 *
	 * @return {@code Class<E> }
	 * @throws
	 * @see Class<E>
	 */
	@SuppressWarnings("unchecked")
	private Class<E> currentModelCls() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
	}

	/**
	 * 批量公共
	 *
	 * @param requestData 请求数据
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ResourcePublicReq
	 * @see BaseRsp<Boolean>
	 */
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
	 *
	 * @param collection
	 * @param mapperValue
	 * @return
	 * @throws
	 * @see Collection<U>
	 * @see Function<U, V>
	 * @see Boolean
	 */
	protected <U, V> Boolean updateData(Collection<U> collection, Function<U, V> mapperValue) {
		List<V> publicDatas = StreamApiUtils.map(collection, mapperValue);
		List<E> entityList = BeanUtils.deepCopyList(publicDatas, entityClass);
		boolean result = true;
		for (E entity : entityList) {
			result &= baseService.updateById(entity);
		}
		return result;
	}

	/**
	 * 数据中间存储
	 */
	@Getter
@Setter
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
