/**
 * 这段代码定义了一个名为 `SpaceTacticDefensePlan` 的实体类，用于表示空间战法防御计划。以下是代码的简要分析： 
 * 1. **类定义**： 
 * - 类 `SpaceTacticDefensePlan` 继承自 `SpaceBaseTable`。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 2. **注解**： 
 * - `@TableName("space_tactic_defense_plan")`：指定该实体对应的数据库表名为 `space_tactic_defense_plan`。 
 * 3. **字段**： 
 * - `description`：描述信息。 
 * - `plan`：具体的防御计划内容。 
 * 4. **方法**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为 `SpaceTacticDefensePlanBo` 对象。使用了 `BeanUtils.copy` 方法进行对象拷贝。 
 * 5. **依赖**： 
 * - 使用了 `BeanUtils` 进行对象之间的属性复制。 
 * - 引入了 `IdAndName` 和 `SpaceBaseTable` 模型类。 
 * - 引入了 `SpaceTacticDefensePlanBo` 业务对象。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，用于数据库操作和业务逻辑之间的数据映射。通过 `entity2Bo` 方法实现了从实体到业务对象的转换。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.tactic.SpaceTacticDefensePlanBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * 空间战法防御计划
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_tactic_defense_plan")
public class SpaceTacticDefensePlan extends SpaceBaseTable {


	/**
	 * 描述
	 */
	private String description;

	private String plan;

	public SpaceTacticDefensePlanBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTacticDefensePlanBo bo = BeanUtils.copy(this, SpaceTacticDefensePlanBo.class);
		return bo;
    }
}
    