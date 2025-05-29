package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `Tool` 的 Java 类，该类继承自 `BaseTable`，用于表示工具实体。以下是对其主要部分的分析： 
 * ### 类定义与注解 
 * - **包名**：`com.cyberpeace.cr.tool.dao.entity` 
 * - **导入**：使用了 MyBatis Plus 和 Lombok 库，并且引入了一些自定义的工具类。 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动为所有字段生成 getter 和 setter 方法。 
 * - `@TableName`：指定该类对应的数据库表名为 `cr_tool_tool`。 
 * ### 常量定义 
 * - 定义了两个静态常量 `ONLINE` 和 `OFFLINE`，分别表示工具的在线状态（1 表示在线，0 表示离线）。 
 * ### 字段定义 
 * - `resourceId`：工具的资源 ID，使用 UUID 作为主键。 
 * - `name`：工具的名称。 
 * - `cover`：工具的封面。 
 * - `homepage`：工具的主页（已弃用）。 
 * - `category`：工具的类别。 
 * - `manual`：工具的手册（已弃用）。 
 * - `imageResourceId`：图像资源 ID。 
 * - `visible`：工具是否可见。 
 * - `toolType`：工具类型（0 表示离线，1 表示在线）。 
 * - `platforms`：工具支持的平台。 
 * - `language`：工具支持的语言。 
 * - `introduction`：工具的介绍。 
 * - `toolPublic`：工具是否公开（已弃用）。 
 * - `cracked`：工具是否被破解。 
 * - `disabled`：工具是否被禁用。 
 * - `tag`：工具的标签。 
 * - `scene`：工具的应用场景。 
 * - `version`：工具的版本。 
 * - `files`：工具的文件。 
 * - `coverUrl`：工具封面的 URL（虚拟字段，不会持久化到数据库）。 
 * - `type`：工具的类型。 
 * - `builtin`：工具是否内置。 
 * ### 方法定义 
 * - `getCoverUrl()`：获取封面 URL，如果封面路径不以配置的前缀开头，则添加前缀。 
 * ### 枚举定义 
 * - `Cracked`：枚举类型，表示工具的破解状态。 
 * - `Type`：枚举类型，表示工具的类型。 
 * ### 总结 
 * - 这个类主要用于表示工具信息，包括工具的基本属性、状态和类型等。 
 * - 使用了 Lombok 注解来简化代码，减少样板代码。 
 * - 部分字段已弃用，表明可能存在历史遗留问题或未来的重构需求。 
 * - 通过枚举类型定义了工具的状态和类型，增加了代码的可读性和可维护性。 
**/
@Getter
@Setter
@TableName("cr_tool_tool")
public class Tool extends BaseTable {

	/**
	 * 在线
	 */// 在线
	public static final Integer ONLINE = 1;

	/**
	 * 离线
	 */// 离线
	public static final Integer OFFLINE = 0;

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 盖
	 */
	private String cover;

	/**
	 * 主页
	 */
	@Deprecated
	private String homepage;

	/**
	 * 类别
	 */
	private String category;

	/**
	 * 手动
	 */
	@Deprecated
	private String manual;

	/**
	 * 图像资源id
	 */
	private String imageResourceId;

	/**
	 * 可见
	 */
	private Boolean visible;

	/**
	 * 工具类型
	 */// 0 离线， 1在线
	private Integer toolType;

	/**
	 * 平台
	 */
	private String platforms;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 介绍
	 */
	private String introduction;

	/**
	 * 工具 public
	 */
	@Deprecated
	@TableField("public")
	private Integer toolPublic;

	/**
	 * 破获
	 */
	private Boolean cracked;

	/**
	 * 禁用
	 */
	private Boolean disabled;

	/**
	 * 标记
	 */
	private String tag;

	/**
	 * 场景
	 */
	private String scene;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 文件
	 */
	private String files;

	/**
	 * 封面 URL
	 */
	@TableField(exist = false)
	private String coverUrl;

	/**
	 * 类型
	 */
	private String type;

	private Boolean builtin;

	public String getCoverUrl() {
		String prefix = ConfigUtil.getContextProperty("cr.local.file.prefix");
		if (StringUtils.isNotBlank(cover) && !cover.startsWith(prefix)) {
			return prefix + cover;
		}
		return cover;
	}

	public enum Cracked {

		FREE("free", "免费"), TRIAL("trial", "限制"), NON_FREE("non-free", "收费");

		@Getter
		private String code;

		@Getter
		private String desc;

		Cracked(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum Type {

		PROBE("probe", "探测类"), ATTACK("attack", "攻击类"),
		CONTROL("control", "控制类"), DEFENSE("defense", "防御类")
		, OTHER("other", "其他类");

		@Getter
		private String code;

		@Getter
		private String desc;

		Type(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
