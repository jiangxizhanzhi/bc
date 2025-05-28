/**
 * 这段代码定义了一个名为 `SpaceTacticDefensePlanBo` 的类，它是基于 `SpaceBoBase` 类的扩展。这个类主要用于表示空间战法防御计划的业务对象（BO），并提供了一个方法将该业务对象转换为实体对象。 
 * ### 主要功能分析： 
 * 1. **属性定义**：类中定义了几个关键属性，包括资源ID、名称、描述、层级等，并通过 `@ApiModelProperty` 注解为这些属性提供了元数据信息。 
 * 2. **继承关系**：该类继承自 `SpaceBoBase`，表明它可能包含了父类的一些通用属性或方法。 
 * 3. **方法实现**：`bo2Entity()` 方法实现了从业务对象到实体对象的转换逻辑。此方法使用了 `BeanUtils.copy` 来复制当前对象的所有字段到一个新的 `SpaceTacticDefensePlan` 实例中，并且检查了层级的有效性。此外，它还处理了层级结构中的子元素，并将它们的资源ID组合成一个字符串形式存储在实体对象的计划字段中。 
 * 4. **工具类使用**：代码中使用了 `BeanUtils` 和 `JsonUtil` 工具类来进行对象之间的转换以及数据格式化操作。 
 * 5. **注释说明**：类和主要方法都有详细的注释，解释了其用途和工作原理，有助于其他开发者理解代码的功能。 
 * 总体来说，这段代码设计合理，清晰地展示了如何从业务层的数据模型向持久化层的数据模型进行映射的过程。 
**/

package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.model.SpaceBoBase;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 空间战法防御计划Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceTacticDefensePlanBo extends SpaceBoBase {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "简述")
	private String description;

	/**
	 * 层级
	 *
	 * @see SpaceFrameLevel
	 */
	@ApiModelProperty(value = "层级框架")
	private SpaceFrameLevel level;

	/**
	 * BO2 实体
	 *
	 * @return 空间战法防御计划
	 * @throws
	 * @see SpaceTacticDefensePlan
	 */
	public SpaceTacticDefensePlan bo2Entity() {
		SpaceTacticDefensePlan entity = BeanUtils.copy(this, SpaceTacticDefensePlan.class);
		this.level.checkLevel(true);
		List<String> defenseIds = Lists.newArrayList();
		for (SpaceFrameLevel category : level.getChildren()) {
			for (SpaceFrameLevel defense : category.getChildren()) {
				defenseIds.add(defense.getResourceId());
			}
		}
		entity.setPlan(JsonUtil.arrayToString(defenseIds));
		return entity;
	}

}
    