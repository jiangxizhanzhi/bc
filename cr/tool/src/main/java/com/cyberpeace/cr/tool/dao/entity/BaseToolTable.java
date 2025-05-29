package com.cyberpeace.cr.tool.dao.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础刀具表
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `BaseToolTable` 的 Java 类，该类实现了 `Serializable` 接口。这个类代表一个基础刀具表（根据类注释）。以下是该类的主要组成部分和特点： 
 * 1. **包声明**：该类位于 `com.cyberpeace.cr.tool.dao.entity` 包中。 
 * 2. **导入语句**： 
 * - `com.baomidou.mybatisplus.annotation.FieldFill` 和 `com.baomidou.mybatisplus.annotation.TableField` 用于处理数据库字段的自动填充。 
 * - `lombok.Getter` 和 `lombok.Setter` 注解用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable` 用于实现序列化接口，使得对象可以被序列化和反序列化。 
 * 3. **类注释**： 
 * - 文档注释描述了该类的功能（基础刀具表）以及作者和日期信息。 
 * 4. **类属性**： 
 * - `id`：Integer 类型，表示唯一标识符。 
 * - `status`：String 类型，表示状态。 
 * - `createTime`：Date 类型，表示创建时间，并且使用了 Lombok 的 `@Setter` 和 `@Getter` 注解。 
 * - `title`：String 类型，表示标题。 
 * - `edbId`：Integer 类型，表示教育局 ID。 
 * - `author`：String 类型，表示作者。 
 * - `type`：String 类型，表示类型。 
 * - `platform`：String 类型，表示平台。 
 * - `suffix`：String 类型，表示后缀。 
 * - `content`：String 类型，表示内容。 
 * 5. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：Lombok 注解用于自动生成属性的 getter 和 setter 方法，减少样板代码。 
 * 6. **数据库字段注解**： 
 * - `@TableField(fill = FieldFill.INSERT)`：表示 `createTime` 字段在插入记录时会自动填充当前时间。 
 * 总体来说，这是一个简单的实体类，用于表示基础刀具表的数据结构，并且通过 Lombok 减少了手动编写 getter 和 setter 方法的工作量。同时，使用 MyBatis-Plus 提供的注解来处理字段的自动填充功能。 
**/
@Getter
@Setter
public class BaseToolTable implements Serializable {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 教育局id
	 */
	private Integer edbId;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 平台
	 */
	private String platform;

	/**
	 * 后缀
	 */
	private String suffix;

	/**
	 * 内容
	 */
	private String content;

}

