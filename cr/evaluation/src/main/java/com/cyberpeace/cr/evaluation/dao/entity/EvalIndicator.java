/**
 * 这段代码定义了一个名为 `EvalIndicator` 的 Java 类，该类继承自 `BaseTable`。这个类表示一个评价指标（Evaluation Indicator）实体，并且与数据库表 `cr_eval_indicator` 相关联。以下是对代码的简要分析： 
 * ### 主要特点： 
 * 1. **继承关系**： 
 * - `EvalIndicator` 继承了 `BaseTable` 类。`BaseTable` 可能是一个通用的数据访问对象 (DAO) 基础类，包含了与数据库交互的一些基础方法。 
 * 2. **注解**： 
 * - `@TableName("cr_eval_indicator")`：标明该类对应的数据库表名为 `cr_eval_indicator`。 
 * - `@TableId`：用于指定主键字段及其生成策略。在本例中，`resource_id` 字段作为主键，采用 UUID 生成策略。 
 * - `@TableField`：用于指定字段的特殊配置。例如，`attributeValue` 字段在更新时被忽略。 
 * 3. **字段**： 
 * - `resourceId`：主键，存储资源 ID。 
 * - `name`：指标的名称。 
 * - `description`：指标的描述信息。 
 * - `datatype`：数据类型，使用整数表示。 
 * - `attribute`：指标属性，同样使用整数表示。 
 * - `attributeValue`：指标属性值，在更新时不参与操作。 
 * - `connlist`：连接线，存储字符串形式的数据。 
 * - `aggregatingAlgorithmId`：聚合算法 ID。 
 * - `aggregatingAlgorithmParams`：聚合算法参数。 
 * - `aggregationIds`：关联聚合项 ID。 
 * - `collectionIds`：关联变量项 ID。 
 * - `createUserId`：创建者的用户 ID。 
 * - `matrixData`：矩阵内容。 
 * 4. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法，简化了代码编写。 
 * ### 总结： 
 * - `EvalIndicator` 类是 `cr_eval_indicator` 表的 Java 实体映射。 
 * - 它包含了一些基本的属性如名称、描述等，以及一些复杂的属性如聚合算法 ID 和参数。 
 * - 使用了 Lombok 注解来减少样板代码。 
 * - 通过 MyBatis Plus 的注解实现了与数据库的映射，包括主键生成策略和字段忽略策略。 
 * 这个类的设计符合面向对象编程的基本原则，能够方便地进行数据库操作和业务逻辑处理。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @TableName cr_eval_indicator
 */
@TableName(value = "cr_eval_indicator")
@Getter
@Setter
public class EvalIndicator extends BaseTable {

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
	 * 数据类型
	 */
	private Integer datatype;

	/**
	 * 指标属性
	 */
	private Integer attribute;

	/**
	 * 指标属性值
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer attributeValue;

	/**
	 * 连接线
	 */
	private String connlist;

	/**
	 * 聚合算法id
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 关联聚合项id
	 */
	private String aggregationIds;

	/**
	 * 关联变量项id
	 */
	private String collectionIds;

	/**
	 * 创建人
	 */
	private String createUserId;

	/**
	 * 矩阵内容
	 */
	private String matrixData;
}