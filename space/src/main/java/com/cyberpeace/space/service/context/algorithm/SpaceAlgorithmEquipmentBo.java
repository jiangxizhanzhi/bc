package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmEquipment;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间算法设备 BO
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/05
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAlgorithmEquipmentBo` 的 Java 类，它继承自 `SpaceBoBase` 并用于表示空间算法设备的业务对象（BO）。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `SpaceAlgorithmEquipmentBo` 是一个数据传输对象（DTO），用于封装与空间算法设备相关的业务逻辑和属性。 
 * 2. **继承与注解**： 
 * - 继承自 `SpaceBoBase`，可能是一个通用的基础类。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法，减少样板代码。 
 * 3. **字段说明**： 
 * - 包含多个字段，如 `resourceId`, `name`, `description` 等，每个字段都有对应的 Swagger 注解 `@ApiModelProperty`，用于生成 API 文档。 
 * - 字段类型包括字符串 (`String`) 和整数 (`Integer`)，分别表示不同的设备属性。 
 * 4. **方法**： 
 * - `bo2Entity()` 方法将当前对象转换为 `SpaceAlgorithmEquipment` 实体对象。使用了 `BeanUtils.copy` 方法来实现对象之间的属性复制。 
 * 5. **用途**： 
 * - 该类主要用于在业务层和服务层之间传递数据，同时提供将业务对象转换为实体对象的能力。 
 * 总结：这是一个典型的业务对象类，结合了 Lombok 和 Swagger 注解，提供了简洁的数据管理和转换功能。 
**/

@Getter
@Setter
public class SpaceAlgorithmEquipmentBo extends SpaceBoBase {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "算法名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "装备描述")
	private String description;

	/**
	 * 资产管理
	 */
	@ApiModelProperty(value = "资产管理能力")
	private Integer assetManagement;

	/**
	 * 行为管理
	 */
	@ApiModelProperty(value = "行为管理能力")
	private Integer behaviorManagement;

	/**
	 * 模块隔离
	 */
	@ApiModelProperty(value = "隔离模块能力")
	private Integer moduleIsolation;

	/**
	 * 风险发现
	 */
	@ApiModelProperty(value = "风险发现能力")
	private Integer riskDiscovery;

	/**
	 * 威胁监控
	 */
	@ApiModelProperty(value = "威胁监测能力")
	private Integer threatMonitoring;

	/**
	 * 策略
	 */
	@ApiModelProperty(value = "策略能力")
	private Integer strategy;

	/**
	 * 能力
	 */
	@ApiModelProperty(value = "性能")
	private Integer capability;

	public SpaceAlgorithmEquipment bo2Entity() {
		SpaceAlgorithmEquipment entity = BeanUtils.copy(this, SpaceAlgorithmEquipment.class);
		return entity;
	}

}

