package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 空间作方法
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionMethod` 的实体类，继承自 `SpaceFrameBaseTable`，并使用了 MyBatis-Plus 的 `@TableName` 注解来映射数据库表。以下是代码的主要分析： 
 * 1. **类与注解**： 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 提供，用于自动生成 getter 和 setter 方法。 
 * - `@TableName("space_action_method")` 指定该类对应的数据库表名为 `space_action_method`。 
 * 2. **字段**： 
 * - `description`: 描述信息。 
 * - `tag`: 标签信息。 
 * 3. **方法**： 
 * - `entity2Bo(Map<String, List<IdAndName>> relatedMap)`：将当前实体对象转换为业务对象 `SpaceActionMethodBo`。此方法使用了 `BeanUtils.copy` 来复制属性，并通过 `JsonUtil.toJsonObjectArray` 将字符串类型的 `tag` 转换为 JSON 对象数组。 
 * 4. **依赖的类和工具**： 
 * - `SpaceFrameBaseTable`: 基础表类，可能包含一些通用字段或方法。 
 * - `SpaceActionMethodBo`: 业务对象类。 
 * - `IdAndName`: 包含 ID 和名称的对象。 
 * - `BeanUtils` 和 `JsonUtil`: 工具类，分别用于对象复制和 JSON 操作。 
 * 总结：这是一个典型的持久层实体类，主要用于数据库操作和对象转换。 
**/
@Getter
@Setter
@TableName("space_action_method")
public class SpaceActionMethod extends SpaceFrameBaseTable {

	private String description;

	private String tag;

	public SpaceActionMethodBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionMethodBo bo = BeanUtils.copy(this, SpaceActionMethodBo.class);
		// bo.setTarget(relatedMap.get(bo.getResourceId()));
		bo.setTag(JsonUtil.toJsonObjectArray(this.tag, String.class));
		return bo;
	}
}
