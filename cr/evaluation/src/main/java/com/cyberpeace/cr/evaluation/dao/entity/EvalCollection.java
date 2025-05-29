package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @TableName cr_eval_collection
 */
/**
 * 这段代码定义了一个名为 `EvalCollection` 的 Java 类，该类位于包 `com.cyberpeace.cr.evaluation.dao.entity` 中。以下是对其主要特性和功能的简要分析： 
 * 1. **继承关系**： 
 * - `EvalCollection` 继承自 `BaseTable` 类。`BaseTable` 类可能是一个通用的数据表模型类，用于提供一些基本的数据表操作功能。 
 * 2. **数据表注解**： 
 * - 使用了 MyBatis-Plus 提供的 `@TableName` 注解，指明该类对应的数据库表名为 `cr_eval_collection`。 
 * 3. **属性字段**： 
 * - `resourceId`: 唯一标识符，使用 UUID 作为主键。 
 * - `name`: 名称字段，用于存储名称信息。 
 * - `evalIndicatorId`, `formatAlgorithmId`, `detectionAlgorithmId`, `scoreAlgorithmId`: 分别表示关联指标项、格式化算法、检测算法和评分算法的 ID 字段。 
 * - `formatAlgorithmParams`, `detectionAlgorithmParams`, `scoreAlgorithmParams`: 存储相应的算法参数。 
 * 4. **Getter 和 Setter 方法**： 
 * - 使用 Lombok 库中的 `@Getter` 和 `@Setter` 注解，自动生成所有属性的 getter 和 setter 方法，简化了代码编写。 
 * 5. **用途**： 
 * - 这个类可能用于表示与评估相关的集合或组，包括与特定评估相关的指标项、算法及其参数。 
 * 6. **依赖库**： 
 * - 代码中使用了 MyBatis-Plus（用于数据库操作）和 Lombok（用于简化 getter 和 setter 方法的生成）。 
 * 综上所述，这段代码定义了一个用于存储评估相关数据的实体类，并且通过继承和注解的方式，使其能够方便地与数据库进行交互。 
**/
@TableName(value = "cr_eval_collection")
@Getter
@Setter
public class EvalCollection extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 关联指标项id
	 */

	private String evalIndicatorId;

	/**
	 * 格式化算法id
	 */
	private String formatAlgorithmId;

	/**
	 * 格式化算法参数
	 */
	private String formatAlgorithmParams;

	/**
	 * 检测算法id
	 */
	private String detectionAlgorithmId;

	/**
	 * 检测算法参数
	 */
	private String detectionAlgorithmParams;

	/**
	 * 评分算法id
	 */
	private String scoreAlgorithmId;

	/**
	 * 评分算法参数
	 */
	private String scoreAlgorithmParams;

}
