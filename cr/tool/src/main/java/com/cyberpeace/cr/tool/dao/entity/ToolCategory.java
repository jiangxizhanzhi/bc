/**
 * 这段代码定义了一个名为 `ToolCategory` 的 Java 类，用于表示工具类别。该类使用了 MyBatis-Plus 和 Lombok 库，并且实现了 `Serializable` 和 `SelectI18nName` 接口。以下是详细的分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.cr.tool.dao.entity` 
 * - **导入**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 
 * - `com.baomidou.mybatisplus.annotation.TableId` 
 * - `com.baomidou.mybatisplus.annotation.TableName` 
 * - `com.cyberpeace.constant.SelectI18nName` 
 * - `io.swagger.annotations.ApiModelProperty` 
 * - `lombok.Getter` 
 * - `lombok.Setter` 
 * ### 类声明 
 * - **类名**：`ToolCategory` 
 * - **注解**： 
 * - `@TableName("cr_tool_toolcategory")`：指定数据库表名为 `cr_tool_toolcategory` 
 * - `@Getter` 和 `@Setter`：使用 Lombok 自动生成 getter 和 setter 方法 
 * ### 实现接口 
 * - `Serializable`：使对象能够序列化 
 * - `SelectI18nName`：实现国际化名称选择功能 
 * ### 成员变量 
 * - **id**：整数类型，表示记录的唯一标识符 
 * - **resourceId**：字符串类型，表示资源 ID，并通过 `@TableId` 注解指定为 UUID 类型 
 * - **cnName**：字符串类型，表示中文名称 
 * - **enName**：字符串类型，表示英文名称 
 * - **type**：字符串类型，表示类别类型 
 * - **serialVersionUID**：静态常量，用于序列化版本控制 
 * ### 方法 
 * - `@Getter` 和 `@Setter` 注解自动生成了所有成员变量的 getter 和 setter 方法 
 * ### 总结 
 * 这个类主要用于数据库表 `cr_tool_toolcategory` 的映射，包含工具类别的基本信息（如资源 ID、中英文名称等）。通过 Lombok 库简化了代码，减少了样板代码。此外，该类还实现了序列化接口和国际化名称选择接口，以支持序列化和多语言处理。 
**/
package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.constant.SelectI18nName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工具类别
 *
 * @author SN
 * @date 2024/11/08
 */
@TableName("cr_tool_toolcategory")
@Getter
@Setter
public class ToolCategory implements Serializable, SelectI18nName {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 资源id
	 */
	@ApiModelProperty("ID")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 中文名称
	 */
	@ApiModelProperty("中文名称")
	private String cnName;

	/**
	 * 中文名称
	 */
	@ApiModelProperty("英文名称")
	private String enName;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 串行版本 UID
	 */
	private static final long serialVersionUID = 1L;
}