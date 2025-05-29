package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间警告字典Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceWarningDictionaryBo` 的类，它是 `SpaceBoBase` 类的子类。以下是代码的主要分析： 
 * 1. **类的继承与注解**： 
 * - `SpaceWarningDictionaryBo` 继承自 `SpaceBoBase`。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 2. **字段及其注释**： 
 * - 包含多个字段，每个字段都有对应的注释说明其用途和类型。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个字段的含义。 
 * 3. **方法**： 
 * - 提供了一个 `bo2Entity()` 方法，用于将当前对象（BO）转换为 `SpaceWarningDictionary` 实体对象。此方法利用了 `BeanUtils.copy()` 方法进行对象的复制。 
 * 4. **用途**： 
 * - 该类主要用于表示空间警告字典的数据传输对象（DTO），并提供从 DTO 到实体对象的转换功能。 
 * 总体而言，这段代码是一个典型的 DTO 类，用于在业务逻辑层和数据访问层之间传递数据，并提供了便捷的对象转换功能。 
**/

@Getter
@Setter
public class SpaceWarningDictionaryBo extends SpaceBoBase {

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
	 * 工具类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具类型")
	private String toolType;

	/**
	 * 工具层级
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具告警等级")
	private String toolLevel;

	/**
	 * 层级
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "告警等级,低1,中2,高3")
	private Integer level;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "等级描述")
	private String description;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;


	/**
	 * BO2 实体
	 *
	 * @return 空间警告词典
	 * @throws
	 * @see SpaceWarningDictionary
	 */
	public SpaceWarningDictionary bo2Entity() {
		SpaceWarningDictionary entity = BeanUtils.copy(this, SpaceWarningDictionary.class);
		return entity;
	}

}
    
