package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmEquipmentBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * 空间算法设备
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * ### 分析与总结 
 * #### 代码功能概述： 
 * 该代码定义了一个名为 `SpaceAlgorithmEquipment` 的实体类，用于表示空间算法设备的相关信息。它继承自 `SpaceBaseTable`，并使用了 MyBatis-Plus 的 `@TableName` 注解来映射数据库表 `space_algorithm_equipment`。 
 * #### 主要特性： 
 * 1. **注解与继承**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - `@TableName` 注解将类与数据库表 `space_algorithm_equipment` 关联。 
 * - 继承自 `SpaceBaseTable`，可能包含一些通用的基础字段或方法。 
 * 2. **字段说明**： 
 * - `description`: 设备描述。 
 * - `assetManagement`, `behaviorManagement`, `moduleIsolation`, `riskDiscovery`, `threatMonitoring`, `strategy`, `capability`: 各种功能模块的标识（整数类型），可能用于表示是否启用或状态。 
 * 3. **方法**： 
 * - `entity2Bo`: 将当前实体对象转换为 `SpaceAlgorithmEquipmentBo` 对象。通过 `BeanUtils.copy` 方法实现，方便业务逻辑中进行对象转换。 
 * 4. **依赖的其他类**： 
 * - `SpaceBaseTable`: 可能是一个基础类，提供了一些通用的功能或字段。 
 * - `IdAndName`: 一个简单的模型类，可能表示 ID 和名称的组合。 
 * - `SpaceAlgorithmEquipmentBo`: 业务对象，用于表示业务逻辑中的数据结构。 
 * - `BeanUtils.copy`: 工具方法，用于对象之间的属性复制。 
 * #### 总结： 
 * 该类主要用于数据库表 `space_algorithm_equipment` 的实体映射，并提供了基本的字段和方法。其核心功能是通过 `entity2Bo` 方法实现从实体到业务对象的转换，便于在业务层使用。 
**/
@Getter
@Setter
@TableName("space_algorithm_equipment")
public class SpaceAlgorithmEquipment extends SpaceBaseTable {

	/**
	 * 描述
	 */
	private String description;
	/**
	 * 资产管理
	 */
	private Integer assetManagement;
	/**
	 * 行为管理
	 */
	private Integer behaviorManagement;
	/**
	 * 模块隔离
	 */
	private Integer moduleIsolation;
	/**
	 * 风险发现
	 */
	private Integer riskDiscovery;
	/**
	 * 威胁监控
	 */
	private Integer threatMonitoring;
	/**
	 * 策略
	 */
	private Integer strategy;
	/**
	 * 能力
	 */
	private Integer capability;

	public SpaceAlgorithmEquipmentBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmEquipmentBo bo = BeanUtils.copy(this, SpaceAlgorithmEquipmentBo.class);
		return bo;
    }
}
    
