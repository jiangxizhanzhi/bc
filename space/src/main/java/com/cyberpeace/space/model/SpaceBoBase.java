package com.cyberpeace.space.model;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间Bo基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceBoBase` 的抽象类，它是空间业务对象（BO）的基础类。以下是对其功能的简要分析： 
 * 1. **类的基本信息**： 
 * - 类位于 `com.cyberpeace.space.model` 包中。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的getter和setter方法。 
 * - 作者为 SN，版本号为 1.0.0。 
 * 2. **属性**： 
 * - `resourceId`：表示资源ID，类型为字符串。 
 * - `builtin`：布尔值，表示是否为内置资源。 
 * 3. **抽象方法**： 
 * - `bo2Entity()`：这是一个抽象方法，要求所有继承该类的具体实现类必须提供其实现。此方法用于将业务对象（BO）转换为实体对象（Entity）。 
 * 4. **实用方法**： 
 * - `getIds(List<IdAndName> list)`：根据传入的 `IdAndName` 列表生成对应的资源ID列表，并将其转换为JSON字符串返回。 
 * - 如果输入列表为 `null`，则返回一个空列表。 
 * - 使用了 Guava 的 `Lists.newArrayList()` 创建空列表。 
 * - 使用了 `StreamApiUtils.map()` 方法将列表中的每个 `IdAndName` 对象映射为其 `resourceId` 属性。 
 * - 最后通过 `JsonUtil.arrayToString()` 将字符串数组转换为JSON格式的字符串。 
 * 总体来说，这个类提供了一个通用的框架来处理与空间相关的业务逻辑，并且包含了一些实用的方法来辅助数据操作。 
**/
@Getter
@Setter
public abstract class SpaceBoBase {

	private String resourceId;
	private Boolean builtin;
	public abstract SpaceBaseTable bo2Entity();

	public String getIds(List<IdAndName> list){
		List<String> ids;
		if(null == list){
			ids = Lists.newArrayList();
		} else {
			ids = StreamApiUtils.map(list, IdAndName::getResourceId);
		}
		return JsonUtil.arrayToString(ids);
	}
}

