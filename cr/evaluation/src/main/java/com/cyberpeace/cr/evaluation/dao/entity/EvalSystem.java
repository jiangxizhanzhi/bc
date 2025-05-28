/**
 * 这段代码定义了一个名为 `EvalSystem` 的 Java 类，该类位于 `com.cyberpeace.cr.evaluation.dao.entity` 包中。这个类继承自 `BaseTable`，并且使用了 Lombok 注解来简化 getter 和 setter 方法的生成。此外，它还使用了 MyBatis-Plus 和 Swagger 注解。 
 * ### 主要功能和特性： 
 * 1. **数据库表映射**： 
 * - 使用 `@TableName` 注解将 `EvalSystem` 类映射到数据库中的 `cr_eval_system` 表。 
 * 2. **属性**： 
 * - `resourceId`：唯一标识符，类型为 UUID。 
 * - `name`：系统名称。 
 * - `description`：系统描述。 
 * - `connlist`：连接线信息。 
 * - `firstIndexNum`：一级指标数量。 
 * - `secondIndexNum`：二级指标数量。 
 * - `thirdIndexNum`：三级指标数量。 
 * - `createUserId`：创建者的用户 ID。 
 * 3. **注解**： 
 * - `@ApiModelProperty`：用于 Swagger 文档生成，描述每个字段的用途。 
 * - `@TableId`：指定主键策略为 UUID，并且对应的数据库列名为 `resource_id`。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 总结： 
 * `EvalSystem` 类主要用于表示和操作评估系统相关的数据。通过继承 `BaseTable`，它可能包含了与数据库操作相关的通用方法。MyBatis-Plus 注解确保了类与数据库表之间的映射关系，而 Swagger 注解则有助于生成 API 文档。整体来看，这是一个典型的实体类，用于在应用程序中表示评估系统的数据模型。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @TableName cr_eval_system
 */
@TableName(value = "cr_eval_system")
@Getter
@Setter
public class EvalSystem extends BaseTable {

	@ApiModelProperty("ID")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String description;

	/**
	 * 连接线
	 */
	@ApiModelProperty("连线信息")
	private String connlist;

	@ApiModelProperty("一级指标数量")
	private Integer firstIndexNum;

	@ApiModelProperty("二级指标数量")
	private Integer secondIndexNum;

	@ApiModelProperty("三级指标数量")
	private Integer thirdIndexNum;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建者ID")
	private String createUserId;
}