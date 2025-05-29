package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间手指打印Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceFingerPrintBo` 的 Java 类，它是基于 `SpaceBoBase` 类的扩展。以下是代码的简要分析： 
 * 1. **类的继承与注解**： 
 * - `SpaceFingerPrintBo` 继承自 `SpaceBoBase`。 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 2. **属性定义**： 
 * - `resourceId`：资源ID，使用 `@ApiModelProperty` 注解描述其为“资源id”。 
 * - `name`：名称，描述为“名称”。 
 * - `type`：类型，描述为“类型”。 
 * - `content`：内容，描述为“指纹列表”。 
 * 3. **方法定义**： 
 * - `bo2Entity()`：将 `SpaceFingerPrintBo` 对象转换为 `SpaceFingerPrint` 实体对象。使用了 `BeanUtils.copy` 方法来实现对象之间的属性复制。 
 * 4. **注释与文档**： 
 * - 类和每个属性都有详细的 Javadoc 注释，说明了类的功能、属性的用途及其数据类型。 
 * - 方法 `bo2Entity()` 也有详细的注释，说明了方法的功能和返回值。 
 * 总体来说，这个类的主要功能是作为业务对象（BO）与实体对象（Entity）之间的桥梁，提供了一种将 BO 转换为 Entity 的便捷方式。 
**/

@Getter
@Setter
public class SpaceFingerPrintBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "指纹列表")
	private String content;

	/**
	 * BO2 实体
	 *
	 * @return 隔间指纹
	 * @throws
	 * @see SpaceFingerPrint
	 */
	public SpaceFingerPrint bo2Entity() {
		SpaceFingerPrint entity = BeanUtils.copy(this, SpaceFingerPrint.class);
		return entity;
	}

}
    
