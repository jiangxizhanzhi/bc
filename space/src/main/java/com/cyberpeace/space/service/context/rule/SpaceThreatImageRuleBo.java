package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间威胁图像规则 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * ### 分析与总结 
 * #### 1. **代码功能概述** 
 * 该代码定义了一个名为 `SpaceThreatImageRuleBo` 的 Java 类，用于表示“空间威胁图像规则”的业务对象（BO）。它继承自 `SpaceBoBase`，并包含了多个字段来描述威胁图像规则的相关属性。此外，还提供了一个方法 `bo2Entity()`，用于将当前 BO 对象转换为对应的实体类 `SpaceThreatImageRule`。 
 * --- 
 * #### 2. **主要特点** 
 * - **继承关系**：`SpaceThreatImageRuleBo` 继承自 `SpaceBoBase`，表明它可能是一个基础业务对象。 
 * - **字段设计**： 
 * - 包含多个注解 `@ApiModelProperty`，说明这些字段会用于 API 文档生成（如 Swagger）。 
 * - 字段涵盖了资源 ID、名称、威胁类型、威胁等级、资产类型、威胁情报、结果和标签等信息。 
 * - **方法实现**： 
 * - 提供了 `bo2Entity()` 方法，通过工具类 `BeanUtils.copy()` 将 BO 对象转换为实体类对象。 
 * --- 
 * #### 3. **代码结构** 
 * - **类声明**： 
 * - 使用 Lombok 注解 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * - **字段注释**： 
 * - 每个字段都有详细的注释，明确描述其用途和含义。 
 * - **方法逻辑**： 
 * - `bo2Entity()` 方法的核心是调用 `BeanUtils.copy()`，这是一种常见的对象映射方式，用于快速将一个对象的值复制到另一个对象中。 
 * --- 
 * #### 4. **优点** 
 * - **清晰的注释**：每个字段和方法都有详细的注释，便于开发者理解其用途。 
 * - **复用性**：通过继承和工具类方法，减少了重复代码。 
 * - **灵活性**：支持从 BO 到实体类的转换，适用于持久化场景。 
 * --- 
 * #### 5. **潜在改进点** 
 * - **异常处理**：在 `bo2Entity()` 方法中，如果 `BeanUtils.copy()` 抛出异常，未进行捕获或处理。 
 * - **泛型支持**：`BeanUtils.copy()` 可能需要更灵活的泛型支持，以增强可扩展性。 
 * - **字段校验**：某些字段（如 `type` 和 `level`）可以考虑增加校验逻辑，确保输入数据的合法性。 
 * --- 
 * #### 6. **适用场景** 
 * 该类适用于需要处理“空间威胁图像规则”相关业务场景的应用程序，特别是在需要将业务对象与数据库实体之间进行转换的情况下。 
 * --- 
 * #### 总结 
 * 这段代码实现了对“空间威胁图像规则”的封装，结构清晰，注释详细，功能实用。但在异常处理和字段校验方面可以进一步优化，以提高代码的健壮性和安全性。 
**/
@Getter
@Setter
public class SpaceThreatImageRuleBo extends SpaceBoBase {

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
	 */
	@ApiModelProperty(value = "威胁类型, 1:僵尸攻击,2:木马攻击，3:蠕虫攻击")
	private Integer type;

	/**
	 * 水平
	 */
	@ApiModelProperty(value = "威胁等级，1:低威胁，2:中威胁，3:高威胁")
	private Integer level;

	/**
	 * 资产类型
	 */
	@ApiModelProperty(value = "受害资产类型, 1:终端设备,2:网络设备,3:服务器设备")
	private Integer assetType;

	/**
	 * 信息
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "威胁情报")
	private String information;

	/**
	 * 结果
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "结果，1:尝试,2:成功,3:失败")
	private Integer result;

	/**
	 * 标记
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "标签")
	private String tag;

	/**
	 * BO2 实体
	 *
	 * @return {@code SpaceThreatImageRule }
	 */
	public SpaceThreatImageRule bo2Entity() {
		SpaceThreatImageRule entity = BeanUtils.copy(this, SpaceThreatImageRule.class);
		return entity;
	}

}
    
