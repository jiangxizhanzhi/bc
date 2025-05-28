/**
 * 这段代码定义了一个名为 `ToolGhdb` 的 Java 类，用于表示一个工具或数据库中的记录。以下是详细的分析： 
 * ### 包声明和导入 
 * - `package com.cyberpeace.cr.tool.dao.entity;`：指定了类所在的包。 
 * - 导入了必要的类： 
 * - `com.baomidou.mybatisplus.annotation.TableId` 和 `com.baomidou.mybatisplus.annotation.TableName`：MyBatis-Plus 注解，用于数据库映射。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：实现序列化接口。 
 * ### 类注释 
 * - 类注释描述了这个类是一个工具 GHDB，并标注了作者和日期。 
 * ### 类声明 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动为所有字段生成 getter 和 setter 方法。 
 * - `@TableName("cr_tool_ghdb")`：指定该类对应的数据库表名。 
 * ### 字段 
 * - `private Integer id;`：主键字段，使用 `@TableId` 注解标记。 
 * - `private String title;`：标题字段。 
 * - `private Integer ghdbId;`：GHDB 数据库ID字段。 
 * - `private String googleSearch;`：Google搜索字段。 
 * - `private String author;`：作者字段。 
 * - `private String published;`：发表字段。 
 * - `private String googleDorkDescription;`：Google Dork 说明字段。 
 * - `private String content;`：内容字段。 
 * - `private Integer status;`：状态字段。 
 * - `private String createTime;`：创建时间字段。 
 * ### 总结 
 * - `ToolGhdb` 类主要用于存储与工具或数据库相关的数据记录。 
 * - 使用了 MyBatis-Plus 和 Lombok 来简化开发过程，减少了样板代码。 
 * - 字段包括基本的信息字段（如标题、内容等）以及一些状态和时间信息。 
 * 这个类可以用于在数据库中持久化和检索工具或数据库的相关信息。 
**/
package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工具 GHDB
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_tool_ghdb")
public class ToolGhdb implements Serializable {

	/**
	 * id
	 */
	@TableId
	private Integer id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * GHDB 数据库id
	 */
	private Integer ghdbId;

	/**
	 * Google 搜索
	 */
	private String googleSearch;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 发表
	 */
	private String published;

	/**
	 * Google Dork 说明
	 */
	private String googleDorkDescription;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private String createTime;
}
