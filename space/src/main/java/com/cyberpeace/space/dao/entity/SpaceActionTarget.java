/**
 * 这段代码定义了一个名为 `SpaceActionTarget` 的实体类，用于表示空间操作的目标。以下是对其的简要分析： 
 * 1. **继承与注解**： 
 * - 该类继承自 `SpaceFrameBaseTable`，表明它是一个基于框架的基础表。 
 * - 使用了 `@TableName("space_action_target")` 注解，指定数据库中的表名为 `space_action_target`。 
 * 2. **字段**： 
 * - 包含一个字段 `description`，用于存储目标的描述信息。 
 * 3. **方法**： 
 * - 提供了一个 `entity2Bo` 方法，用于将当前对象转换为 `SpaceActionTargetBo` 对象。此方法接收一个 `Map<String, List<IdAndName>>` 类型的参数 `relatedMap`，并使用 `BeanUtils.copy` 方法进行属性复制，同时设置资源 ID 对应的值。 
 * 4. **功能**： 
 * - 主要用于在业务逻辑层和数据访问层之间进行对象转换，便于数据处理和传递。 
 * 总结：这是一个典型的实体类，用于数据库操作和业务逻辑之间的数据映射，支持从实体到业务对象的转换。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 空间作目标
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_action_target")
public class SpaceActionTarget extends SpaceFrameBaseTable {

	private String description;

	public SpaceActionTargetBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionTargetBo bo = BeanUtils.copy(this, SpaceActionTargetBo.class);
		bo.setFrame(relatedMap.get(bo.getResourceId()));
		return bo;
	}
}