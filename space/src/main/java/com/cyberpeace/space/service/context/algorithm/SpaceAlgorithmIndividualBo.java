/**
 * 这段代码定义了一个名为 `SpaceAlgorithmIndividualBo` 的类，它是 `SpaceBoBase` 类的子类。以下是对代码的简要分析： 
 * 1. **类的功能**： 
 * - `SpaceAlgorithmIndividualBo` 是一个业务对象（BO），用于表示与空间算法相关的个人数据。 
 * - 它继承自 `SpaceBoBase`，并添加了特定于个人的空间算法属性。 
 * 2. **字段说明**： 
 * - `resourceId`：表示资源的唯一标识符。 
 * - `name`：个人的名称。 
 * - `expertisePercent`：专业知识的百分比。 
 * - `rolePercent`：岗位能力的百分比。 
 * 3. **注解**： 
 * - 使用了 `@Getter` 和 `@Setter` 注解，自动生成字段的getter和setter方法。 
 * - `@ApiModelProperty` 注解用于Swagger文档生成，描述每个字段的含义。 
 * 4. **方法**： 
 * - `bo2Entity()`：将当前业务对象转换为实体对象 `SpaceAlgorithmIndividual`。使用了 `BeanUtils.copy` 方法来实现对象之间的属性复制。 
 * 5. **继承关系**： 
 * - 继承自 `SpaceBoBase`，可能包含一些通用的字段或方法。 
 * 总结：该类主要用于封装与空间算法相关的个人数据，并提供从BO到实体对象的转换功能。 
**/

package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmIndividual;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间算法个人Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmIndividualBo extends SpaceBoBase {

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
	public SpaceAlgorithmIndividual bo2Entity() {
		SpaceAlgorithmIndividual entity = BeanUtils.copy(this, SpaceAlgorithmIndividual.class);
		return entity;
	}

}
    