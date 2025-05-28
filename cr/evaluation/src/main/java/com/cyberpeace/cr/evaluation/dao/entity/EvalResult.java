/**
 * 这段代码定义了一个名为 `EvalResult` 的 Java 类，该类用于表示与评估结果相关的实体。以下是对代码的简要分析： 
 * 1. **包声明**： 
 * - `com.cyberpeace.cr.evaluation.dao.entity`：这个类位于 `com.cyberpeace.cr.evaluation.dao.entity` 包中。 
 * 2. **导入的依赖库**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 和 `com.baomidou.mybatisplus.annotation.TableId`：这些是 MyBatis-Plus 库中的注解，用于处理数据库表的主键。 
 * - `com.baomidou.mybatisplus.annotation.TableName`：这个注解用于指定对应的数据库表名。 
 * - `com.cyberpeace.common.model.BaseTable`：这是一个基础表类，可能包含一些通用字段或方法。 
 * - `io.swagger.annotations.ApiModelProperty`：这个注解用于生成 Swagger 文档中的模型属性描述。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * 3. **类声明**： 
 * - `@TableName(value = "cr_eval_result")`：这个注解指定了数据库表的名称为 `cr_eval_result`。 
 * - `@Getter` 和 `@Setter`：这两个 Lombok 注解自动生成了所有字段的 getter 和 setter 方法。 
 * - `public class EvalResult extends BaseTable`：定义了 `EvalResult` 类继承自 `BaseTable` 类。 
 * 4. **字段**： 
 * - `@TableId(type = IdType.UUID, value = "resource_id") private String resourceId;`：这个字段被标记为主键，并且类型为 UUID。 
 * - `private String record;`：表示评估运行实例的 ID。 
 * - `private Integer firstIndexRes;`：表示一级指标得分。 
 * - `private String secondIndexRes;`：表示二级指标得分。 
 * - `private String thirdIndexRes;`：表示三级指标得分。 
 * - `private String fourthIndexRes;`：表示四级指标得分。 
 * - `private String fifthIndexRes;`：表示五级指标得分。 
 * - `private Integer exeCycle;`：表示执行轮次。 
 * 5. **注释**： 
 * - 每个字段都有相应的注释，描述了字段的用途。 
 * 总结： 
 * - `EvalResult` 类是一个数据传输对象（DTO），用于表示评估结果的数据模型。 
 * - 它使用了 MyBatis-Plus 和 Lombok 等库来简化开发过程。 
 * - 这个类继承自 `BaseTable`，可能包含了其他通用的功能或字段。 
 * - 字段包括主键 `resource_id`、评估运行实例 ID `record`、各级指标得分以及执行轮次 `exeCycle`。 
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
 * @TableName cr_eval_result
 */
@TableName(value = "cr_eval_result")
@Getter
@Setter
public class EvalResult extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 评估运行实例id
	 */
	private String record;

	/**
	 * 一级指标得分
	 */

	private Integer firstIndexRes;

	/**
	 * 二级指标得分
	 */
	private String secondIndexRes;

	/**
	 * 三级指标得分
	 */
	private String thirdIndexRes;

	/**
	 * 四级指标得分
	 */
	private String fourthIndexRes;

	/**
	 * 五级指标得分
	 */
	private String fifthIndexRes;

	/**
	 * 执行轮次
	 */
	private Integer exeCycle;


}