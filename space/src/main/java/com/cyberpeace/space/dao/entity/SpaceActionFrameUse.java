/**
 * 这段代码定义了一个名为 `SpaceActionFrameUse` 的实体类，用于描述与数据库表 `space_action_frame_use` 对应的数据模型。以下是简要分析： 
 * ### 主要功能： 
 * - **数据表映射**：该类通过 `@TableName` 注解与数据库中的 `space_action_frame_use` 表关联。 
 * - **字段定义**：定义了四个字段，分别表示 `id`、`type`、`date` 和 `count`。 
 * - **注解说明**： 
 * - `@TableId(type = IdType.AUTO)`：指定 `id` 字段为主键，并自动生成。 
 * - `@TableField("`date`")` 和 `@TableField("`count`")`：处理 SQL 中关键字冲突（如 `date` 和 `count`），用反引号包裹字段名。 
 * - `@ApiModelProperty`：为 `date` 字段添加 API 文档描述。 
 * ### 特点： 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * - 基于 MyBatis-Plus 进行 ORM 操作，简化数据库操作。 
 * 总结来说，这是一个简单的数据模型类，用于存储和操作与空间行动框架相关的数据记录。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架使用
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Setter
@Getter
@TableName("space_action_frame_use")
public class SpaceActionFrameUse {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String type;

	/**
	 * 日期
	 */
	@ApiModelProperty("日期")
	@TableField("`date`")
	private String date;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@TableField("`count`")
	private Integer count;

}
