/**
 * 这段代码定义了一个名为 `EvalAggregation` 的 Java 类，用于表示一个评估聚合的数据实体。以下是对该类的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.dao.entity` 
 * - **导入**： 
 * - 使用了 MyBatis-Plus 框架的一些注解 (`@TableName`, `@TableId`)。 
 * - 使用了 Lombok 注解 (`@Getter`, `@Setter`) 来简化 getter 和 setter 方法的生成。 
 * - 引入了 `BaseTable` 类，可能是一个基础的表类，包含了一些通用字段或方法。 
 * ### 类声明 
 * - **类名**：`EvalAggregation` 
 * - **继承**：继承自 `BaseTable` 
 * - **注解**： 
 * - `@TableName(value = "cr_eval_aggregation")`：指定该实体对应的数据库表名为 `cr_eval_aggregation`。 
 * - `@Getter`, `@Setter`：使用 Lombok 自动生成 getter 和 setter 方法。 
 * ### 字段 
 * - **resourceId** (`String`)：主键，类型为 UUID。 
 * - **name** (`String`)：名称。 
 * - **description** (`String`)：描述。 
 * - **evalIndicatorId** (`String`)：关联的指标项 ID。 
 * - **collectionIds** (`String`)：关联的采集变量 ID（可能是多个 ID，用逗号或其他分隔符分隔）。 
 * - **aggregatingAlgorithmId** (`String`)：聚合算法 ID。 
 * - **aggregatingAlgorithmParams** (`String`)：聚合算法参数。 
 * - **matrixData** (`String`)：矩阵内容。 
 * ### 总结 
 * - **用途**：`EvalAggregation` 类用于表示评估聚合相关的数据实体。 
 * - **功能**：定义了与评估聚合相关的多个属性，并且通过 MyBatis-Plus 的注解实现了与数据库表的映射。 
 * - **特性**：使用了 Lombok 来减少样板代码，使得类更加简洁。 
 * 这个类可以用于持久化到数据库中，也可以作为数据传输对象（DTO）在系统内部传递数据。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * eval 聚合
 *
 * @author SN
 * @date 2024/11/08
 */
@TableName(value = "cr_eval_aggregation")
@Getter
@Setter
public class EvalAggregation extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 关联指标项id
	 */
	private String evalIndicatorId;

	/**
	 * 关联采集变量id
	 */
	private String collectionIds;

	/**
	 * 聚合算法id
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 矩阵内容
	 */
	private String matrixData;

}