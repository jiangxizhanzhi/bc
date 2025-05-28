/**
 * 这段代码定义了一个名为 `NoteEvaluate` 的 Java 类，该类继承自 `BaseTable`。这个类用于表示与“注意评估”相关的实体对象，并且它使用了 MyBatis-Plus 框架来映射数据库表。下面是对代码的详细分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.oj.ojbase.dao.entity` 
 * - **导入的库**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 和 `com.baomidou.mybatisplus.annotation.TableId`：用于定义主键类型和属性。 
 * - `com.baomidou.mybatisplus.annotation.TableName`：用于指定对应的数据库表名。 
 * - `com.cyberpeace.common.model.BaseTable`：基础表类，可能包含一些通用字段或方法。 
 * - Lombok 注解：`@Getter` 和 `@Setter`，简化了 getter 和 setter 方法的生成。 
 * ### 类注释 
 * - 类名为 `NoteEvaluate`，作者为 SN，版本为 1.0.0，日期为 2024年11月8日。 
 * - 继承了 `BaseTable` 类，表明它是一个数据库表对应的实体类。 
 * ### 类属性 
 * - **resource_id** (`String`)：资源ID，由 `@TableId` 注解标记为主键，类型为 UUID。 
 * - **score** (`Double`)：得分。 
 * - **passed** (`Boolean`)：是否通过。 
 * - **evaluateComment** (`String`)：评估评论。 
 * - **evaluatorId** (`String`)：计算器ID（可能是评估者的ID）。 
 * - **noteId** (`String`)：注意ID。 
 * - **evaluateTime** (`Date`)：评估时间。 
 * ### 特点 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动提供属性的访问器和修改器。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解来指定数据库表名。 
 * - 使用了 MyBatis-Plus 的 `@TableId` 注解来指定主键及其类型。 
 * ### 总结 
 * 这段代码定义了一个用于存储评估信息的实体类 `NoteEvaluate`。它继承自一个基础表类 `BaseTable`，并使用了一些框架注解来简化开发。类中的属性代表了评估记录中的不同字段，如资源ID、得分、是否通过等。 
**/
package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 注意评估
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_note_evaluate")
public class NoteEvaluate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	private Double score;

	/**
	 * 通过
	 *
	 * @see Boolean
	 */
	private Boolean passed;

	/**
	 * 评估评论
	 *
	 * @see String
	 */
	private String evaluateComment;

	/**
	 * 计算器id
	 *
	 * @see String
	 */
	private String evaluatorId;

	/**
	 * 注意id
	 *
	 * @see String
	 */
	private String noteId;

	/**
	 * 评估时间
	 *
	 * @see Date
	 */
	private Date evaluateTime;

}
