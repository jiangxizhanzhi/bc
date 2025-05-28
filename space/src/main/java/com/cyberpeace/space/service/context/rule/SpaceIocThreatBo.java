/**
 * 这段代码定义了一个名为 `SpaceIocThreatBo` 的 Java 类，它继承自 `SpaceBoBase`，并用于表示与 IOC（Indicators of Compromise）威胁相关的业务对象。以下是代码的主要分析和总结： 
 * ### 功能概述 
 * - **类的作用**：`SpaceIocThreatBo` 是一个数据传输对象（DTO），用于封装 IOC 威胁的相关信息，并提供将该对象转换为实体对象的功能。 
 * - **继承关系**：它继承了 `SpaceBoBase`，可能包含一些通用的基础属性或方法。 
 * ### 主要字段 
 * - **resourceId**：资源 ID，唯一标识某个资源。 
 * - **name**：名称，描述威胁的名称。 
 * - **type**：类型，用整数表示威胁的类别。 
 * - **ioc**：IOC 列表，存储具体的 IOC 数据。 
 * ### 方法 
 * - **bo2Entity()**：将当前的业务对象（BO）转换为数据库实体对象（Entity）。使用了 `BeanUtils.copy()` 方法来实现对象之间的属性复制。 
 * ### 特性 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 自动生成字段的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModelProperty` 用于生成 API 文档，描述每个字段的含义。 
 * ### 总结 
 * 这个类是一个典型的 DTO，主要用于在业务逻辑层和数据访问层之间传递数据。它通过继承和工具类实现了对象的转换功能，同时利用注解提升了代码的可读性和开发效率。 
**/

package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间国际 奥委会威胁Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceIocThreatBo extends SpaceBoBase {

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
	 * 国际 奥委会
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "IOC列表")
	private String ioc;

	/**
	 * BO2 实体
	 *
	 * @return SpaceIoC威胁
	 * @throws
	 * @see SpaceIocThreat
	 */
	public SpaceIocThreat bo2Entity() {
		SpaceIocThreat entity = BeanUtils.copy(this, SpaceIocThreat.class);
		return entity;
	}

}
    