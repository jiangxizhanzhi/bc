/**
 * ### 分析与总结 
 * #### 类的功能 
 * `SpaceAlgorithmIndividual` 是一个实体类，继承自 `SpaceBaseTable`，用于表示与“空间算法个体”相关的数据模型。它主要用于数据库表 `space_algorithm_individual` 的映射。 
 * #### 主要属性 
 * - **`expertisePercent`**：表示专业知识的百分比。 
 * - **`rolePercent`**：表示岗位的百分比。 
 * #### 继承关系 
 * - 该类继承了 `SpaceBaseTable`，表明它可能包含一些通用的字段或方法。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的访问和修改操作。 
 * #### 方法 
 * - **`entity2Bo`**：将当前实体对象转换为 `SpaceAlgorithmIndividualBo` 对象。使用了工具类 `BeanUtils.copy` 来完成对象的拷贝。 
 * #### 注意事项 
 * - 依赖外部工具类 `BeanUtils` 和其他模型类（如 `IdAndName`、`SpaceAlgorithmIndividualBo`、`SpaceBaseTable`）。 
 * - 数据库表名通过 `@TableName` 注解指定为 `space_algorithm_individual`。 
 * #### 总结 
 * 这是一个典型的 MyBatis-Plus 实体类，用于数据库操作和业务逻辑中的数据传输。其核心功能是封装与“空间算法个体”相关的数据，并提供对象转换的能力。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmIndividualBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间算法 Individual
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_algorithm_individual")
public class SpaceAlgorithmIndividual extends SpaceBaseTable {

	/**
	 * 专业知识百分比
	 */
	private Integer expertisePercent;

	/**
	 * 岗位百分比
	 */
	private Integer rolePercent;

	public SpaceAlgorithmIndividualBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmIndividualBo bo = BeanUtils.copy(this, SpaceAlgorithmIndividualBo.class);
		return bo;
	}
}
