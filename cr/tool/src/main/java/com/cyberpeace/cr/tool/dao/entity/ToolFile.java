package com.cyberpeace.cr.tool.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 工具文件
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolFile` 的 Java 类，该类用于表示工具文件的相关信息。以下是详细的分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.cr.tool.dao.entity` 
 * - **导入的库**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 
 * - `com.baomidou.mybatisplus.annotation.TableField` 
 * - `com.baomidou.mybatisplus.annotation.TableId` 
 * - `com.baomidou.mybatisplus.annotation.TableName` 
 * - `io.swagger.annotations.ApiModelProperty` 
 * - `lombok.Getter` 
 * - `lombok.Setter` 
 * - `org.apache.commons.lang3.StringUtils` 
 * - `java.io.Serializable` 
 * ### 类定义 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成 getter 和 setter 方法。 
 * - `@TableName("cr_tool_toolfile")`：指定该实体类对应的数据库表名。 
 * - **实现接口**：`Serializable`，使对象能够序列化。 
 * ### 类属性 
 * - **id** (`Integer`)：主键，使用 `@TableId(type = IdType.AUTO)` 注解表示该字段是自动增长的主键。 
 * - **fileName** (`String`)：文件名称，使用 `@ApiModelProperty` 注解描述了该字段在 API 文档中的描述。 
 * - **savePath** (`String`)：文件保存路径，同样使用 `@ApiModelProperty` 注解。 
 * - **fileUrl** (`String`)：文件 URL，使用 `@ApiModelProperty` 注解描述，并通过 `@TableField(exist = false)` 标记为不持久化到数据库中。 
 * ### 自定义方法 
 * - `getFileUrl()`：返回文件的 URL。如果 `savePath` 为空或空白，则返回空字符串；否则返回以 `/cpms/` 开头的 `savePath`。 
 * ### 总结 
 * - `ToolFile` 类用于表示一个工具文件的信息。 
 * - 使用了 MyBatis-Plus 框架进行数据库操作。 
 * - 使用 Lombok 自动生成 getter 和 setter 方法。 
 * - 文件的 URL 是根据 `savePath` 动态生成的。 
 * - 这个类主要用于数据传输对象（DTO）或者数据访问对象（DAO）层。 
**/
@Getter
@Setter
@TableName("cr_tool_toolfile")
public class ToolFile implements Serializable {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 保存路径
	 */
	@ApiModelProperty("文件保存路径")
	private String savePath;

	/**
	 * 文件 URL
	 */
	@ApiModelProperty("文件链接")
	@TableField(exist = false)
	private String fileUrl;

	public String getFileUrl() {
		if (StringUtils.isBlank(savePath)) {
			return "";
		}
		return "/cpms/" + savePath;
	}
}

