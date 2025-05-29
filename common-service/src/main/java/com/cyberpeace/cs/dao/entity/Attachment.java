package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `Attachment` 的 Java 实体类，用于表示“附件”相关的数据模型。以下是简要分析： 
 * 1. **继承关系**： 
 * - `Attachment` 类继承自 `BaseTable`，表明它是一个基于数据库表的实体类，并可能包含一些通用的字段（如创建时间、更新时间等）。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的注解： 
 * - `@Data`：自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成全参构造函数和无参构造函数。 
 * - `@Builder`：为类提供建造者模式支持，方便对象实例化。 
 * - MyBatis-Plus 注解： 
 * - `@TableId(type = IdType.UUID)`：指定 `resourceId` 字段为主键，并使用 UUID 作为生成策略。 
 * - `@TableField("public")`：将字段名 `isPublic` 映射到数据库中的字段 `public`（注意 SQL 中保留字需加引号）。 
 * 3. **字段说明**： 
 * - `resourceId`：资源 ID，主键且通过 UUID 自动生成。 
 * - `fileName`：文件名称。 
 * - `filePath`：文件存储路径。 
 * - `isPublic`：布尔值，表示该附件是否公开。 
 * 4. **用途**： 
 * - 该类通常用于与数据库交互，可能是通过 MyBatis-Plus 框架操作附件表。 
 * 总结：这是一个典型的基于 MyBatis-Plus 和 Lombok 的实体类，设计简洁且功能完善，适用于管理附件相关信息。 
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件路径
	 */
	private String filePath;

	/**
	 * 是否公开
	 */
	@TableField("public")
	private Boolean isPublic;

}

