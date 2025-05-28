/**
 * 这段代码定义了一个抽象控制器类 `PublicController`，用于处理一些通用的业务逻辑。以下是详细的分析和总结： 
 * ### 类定义 
 * - **类名**: `PublicController` 
 * - **泛型参数**: `T` 和 `E`，分别表示基础服务接口和服务实体类。 
 * - **继承**: 该类是一个抽象类，不能被实例化。 
 * ### 成员变量 
 * - **`baseService`**: 通过 `@Autowired` 注解注入的基础服务对象，类型为 `T`。 
 * - **`entityClass`**: 表示当前实体类的类型，通过 `currentModelCls()` 方法初始化。 
 * ### 方法 
 * 1. **`currentModelCls()`** 
 * - 获取当前类的泛型参数类型 `E`。 
 * - 使用 `ReflectionKit.getSuperClassGenericType` 方法从父类中获取泛型参数。 
 * 2. **`batchPublic(ResourcePublicReq requestData)`** 
 * - HTTP PATCH 请求方法，路径为 `/batch-public`。 
 * - 用于批量公开或隐藏资源。 
 * - 需要权限：`CMS_COURSES`, `CMS_EXERCISES`, `CMS_THEORY_PRACTICE`, `CMS_PRACTICE_OPERATION`。 
 * - 调用 `updateData` 方法处理请求数据，并返回结果。 
 * 3. **`updateData(Collection<U> collection, Function<U, V> mapperValue)`** 
 * - 处理批量更新数据的逻辑。 
 * - 将请求数据映射到 `AuthAndPublicData` 对象列表。 
 * - 使用 `BeanUtils.deepCopyList` 方法将对象列表深拷贝为实体类列表。 
 * - 遍历实体类列表，调用 `baseService.updateById` 方法更新每个实体。 
 * ### 内部类 
 * - **`AuthAndPublicData`** 
 * - 用于存储授权和公开信息的数据类。 
 * - 包含字段： 
 * - `resourceId`: 资源ID 
 * - `isPublic`: 是否公开 
 * - `readAuthType`: 阅读权限类型 
 * - `readAuthIds`: 阅读权限ID 
 * - `operationAuthType`: 操作权限类型 
 * - `operationAuthIds`: 操作权限ID 
 * - `modifyUserId`: 修改用户ID 
 * ### 总结 
 * - `PublicController` 是一个抽象控制器类，主要用于处理批量操作（如公开/隐藏资源）。 
 * - 它依赖于基础服务 `baseService` 来执行具体的数据库操作。 
 * - 通过泛型参数 `T` 和 `E`，可以灵活地应用于不同的业务场景。 
 * - `updateData` 方法封装了批量更新的逻辑，提高了代码的复用性和可维护性。 
 * 这个类的设计体现了良好的模块化和面向对象编程的原则，使得代码结构清晰、易于扩展和维护。 
**/
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
