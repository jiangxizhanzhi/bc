package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmTeam;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间算法团队Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAlgorithmTeamBo` 的 Java 类，它是基于 `SpaceBoBase` 的子类，并且主要用于表示空间算法团队的相关业务对象（BO, Business Object）。以下是代码的主要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceAlgorithmTeamBo` 
 * - 包名：`com.cyberpeace.space.service.context.algorithm` 
 * - 该类继承自 `SpaceBoBase`，并且通过 Lombok 注解 `@Getter` 和 `@Setter` 自动生成了所有字段的 getter 和 setter 方法。 
 * 2. **字段说明**： 
 * - `resourceId`：资源ID，用于唯一标识某个资源。 
 * - `name`：团队或成员的名称。 
 * - `expertisePercent`：专业知识的百分比，表示在团队中专业知识所占的比例。 
 * - `rolePercent`：岗位能力的百分比，表示在团队中岗位能力所占的比例。 
 * - 这些字段都使用了 Swagger 的注解 `@ApiModelProperty` 来描述其用途。 
 * 3. **方法说明**： 
 * - `bo2Entity()`：这是一个转换方法，将当前的 BO 对象转换为对应的实体对象 `SpaceAlgorithmTeam`。这里使用了 `BeanUtils.copy` 方法来实现对象之间的属性复制。 
 * 4. **注释与文档**： 
 * - 类上有详细的 Javadoc 注释，说明了该类的作用、作者、版本等信息，并指出了它继承自 `SpaceBoBase`。 
 * - 每个字段都有对应的 Javadoc 注释，清晰地描述了每个字段的含义。 
 * 总体来说，这个类是一个典型的业务对象类，用于封装业务逻辑中的数据结构，并提供了必要的功能（如对象转换）来支持业务操作。 
**/

@Getter
@Setter
public class SpaceAlgorithmTeamBo extends SpaceBoBase {

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
	 * 专业知识百分比
	 */
	@ApiModelProperty(value = "专业能力占比")
	private Integer expertisePercent;

	/**
	 * 岗位百分比
	 */
	@ApiModelProperty(value = "岗位能力占比")
	private Integer rolePercent;

	public SpaceAlgorithmTeam bo2Entity() {
		SpaceAlgorithmTeam entity = BeanUtils.copy(this, SpaceAlgorithmTeam.class);
		return entity;
	}

}
    
