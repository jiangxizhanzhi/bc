/**
 * 这段代码定义了一个名为 `ToolShellCode` 的 Java 类，该类继承自 `BaseToolTable`。这个类表示一个工具壳代号，并且包含了工具壳的相关信息。以下是对这段代码的简要分析： 
 * ### 包和导入 
 * - `com.cyberpeace.cr.tool.dao.entity`: 定义了 `ToolShellCode` 类所在的包。 
 * - 导入了一些常用的库和工具类： 
 * - `com.baomidou.mybatisplus.annotation.TableField` 和 `com.baomidou.mybatisplus.annotation.TableName`：用于 MyBatis-Plus 框架，帮助映射数据库表字段。 
 * - `com.cyberpeace.common.util.ConfigUtil`：提供配置信息的工具类。 
 * - `org.apache.commons.lang3.StringUtils`：提供字符串处理工具方法。 
 * ### 类声明 
 * - `@Getter` 和 `@Setter` 注解：由 Lombok 库提供，自动生成 getter 和 setter 方法。 
 * - `@TableName("cr_tool_shellcode")` 注解：指定该类对应的数据库表名。 
 * - 继承自 `BaseToolTable`：表明 `ToolShellCode` 类继承自某个基类 `BaseToolTable`，可能包含一些通用属性或方法。 
 * ### 属性 
 * - `String size`: 表示工具壳的大小。 
 * - `String published`: 表示是否已发表。 
 * - `Boolean edbVerified`: 表示是否经过 EDB 验证。 
 * - `String shellcode`: 存储 shell 代码。 
 * - `@TableField(exist = false) private String downloadUrl`: 这个字段虽然在数据库中不存在，但可以通过 `getDownloadUrl()` 方法获取下载链接。 
 * ### 方法 
 * - `getDownloadUrl()`: 返回 shell 代码的下载 URL。如果 `shellcode` 字段为空，则返回空字符串；否则，返回配置文件中的前缀加上 `shellcode`。 
 * ### 其他 
 * - `private static final long serialVersionUID = 1L;`: 用于序列化版本控制，确保类的版本兼容性。 
 * ### 总结 
 * `ToolShellCode` 类是一个数据传输对象（DTO），用于表示工具壳的信息。它包含了工具壳的基本属性以及一个计算属性 `downloadUrl`，通过该属性可以动态生成下载链接。此外，这个类使用了 Lombok 库来简化代码，使得开发者不需要手动编写 getter 和 setter 方法。 
**/
package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具壳代号
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_tool_shellcode")
public class ToolShellCode extends BaseToolTable {

	/**
	 * 大小
	 */
	private String size;

	/**
	 * 发表
	 */
	private String published;

	/**
	 * EDB 验证
	 */
	private Boolean edbVerified;

	/**
	 * shell代码
	 */
	private String shellcode;

	/**
	 * 下载 URL
	 */
	@TableField(exist = false)
	private String downloadUrl;

	public String getDownloadUrl() {
		if (StringUtils.isBlank(shellcode)) {
			return "";
		}
		return ConfigUtil.getContextProperty("cr.local.file.prefix") + shellcode;
	}

	/**
	 * 串行版本 UID
	 */
	private static final long serialVersionUID = 1L;
}