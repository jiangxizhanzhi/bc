package com.cyberpeace.cr.tool.controller.res;

import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 工具响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolRsp` 的 Java 类，用于表示工具库的信息。该类使用了 Lombok 库来简化 getter 和 setter 方法的生成，并且使用了 Swagger 注解来描述 API 模型。以下是对该类的简要分析： 
 * ### 主要特性 
 * 1. **继承与实现**： 
 * - `ToolRsp` 类实现了 `Serializable` 接口，这使得对象可以序列化，便于在网络上传输或持久化存储。 
 * 2. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 注解自动生成所有字段的 getter 和 setter 方法，减少了样板代码。 
 * 3. **Swagger 注解**： 
 * - `@ApiModel` 注解描述了这个模型的整体信息。 
 * - `@ApiModelProperty` 注解为每个字段提供了详细的描述，有助于文档生成和接口测试工具（如 Swagger UI）更好地展示 API 信息。 
 * ### 字段描述 
 * - **基本信息**： 
 * - `categoryI18nName`：多个分类的国际化名称，以逗号分隔。 
 * - `cracked`：是否破解。 
 * - `deviceName`：目标设备名称。 
 * - `id`：工具的唯一标识符。 
 * - `language`：支持的语言列表。 
 * - `name`：工具的名称。 
 * - `platforms`：支持的操作系统平台列表。 
 * - `resourceId`：资源 ID。 
 * - `toolType`：工具类型（整数）。 
 * - `category`：工具的分类列表。 
 * - `cover`：工具的封面。 
 * - `coverUrl`：封面的 URL。 
 * - `files`：附件列表（字符串形式）。 
 * - `toolFiles`：附件的详细列表（`ToolFile` 对象的列表）。 
 * - `introduction`：工具的介绍。 
 * - `version`：工具的版本。 
 * - `visible`：工具是否可见。 
 * - `createTime`：创建时间。 
 * - `updateTime`：更新时间。 
 * - **附加信息**： 
 * - `deviceResourceId`：设备资源 ID，默认为空字符串。 
 * - `homePage`：主页 URL，默认为空字符串。 
 * - `imageResourceId`：镜像资源 ID，默认为空字符串。 
 * - `manual`：工具手册。 
 * - `scene`：场景 ID。 
 * - `sceneData`：场景数据。 
 * - `tag`：工具的标签列表。 
 * - `disabled`：是否禁用。 
 * - `type`：工具类型（字符串），例如 `probe`、`attack`、`control`、`defense`。 
 * - `builtin`：是否为内置工具。 
 * ### 总结 
 * `ToolRsp` 类是一个用于表示工具库信息的数据传输对象（DTO）。它包含了工具的基本信息、附加属性以及一些状态信息。通过 Swagger 注解，可以方便地生成 API 文档，便于开发者理解和使用。此外，通过 Lombok 注解，简化了代码编写，减少了重复工作。 
**/
@ApiModel("工具库信息")
@Getter
@Setter
public class ToolRsp implements Serializable {

	/**
	 * 类别i18n名称
	 */
	@ApiModelProperty("多个分类i18n名称逗号分割")
	private String categoryI18nName;

	/**
	 * 破获
	 */
	@ApiModelProperty("是否破解")
	private Boolean cracked;

	/**
	 * 装置名称
	 */
	@ApiModelProperty("靶标名称")
	private String deviceName;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 语言
	 */
	@ApiModelProperty("工具语言")
	private List<String> language;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 平台
	 */
	@ApiModelProperty("平台名称")
	private List<String> platforms;

	/**
	 * 资源id
	 */
	@ApiModelProperty("ID")
	private String resourceId;

	/**
	 * 工具类型
	 */
	@ApiModelProperty("工具类型")
	private Integer toolType;

	/**
	 * 类别
	 */
	@ApiModelProperty("工具分类")
	private List<String> category;

	/**
	 * 盖
	 */
	@ApiModelProperty("封面")
	private String cover;

	/**
	 * 封面 URL
	 */
	@ApiModelProperty("封面连接")
	private String coverUrl;

	/**
	 * 文件
	 */
	@ApiModelProperty("附件列表")
	private String files;

	/**
	 * 工具文件
	 */
	@ApiModelProperty("附件详细列表")
	private List<ToolFile> toolFiles;

	/**
	 * 介绍
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	/**
	 * 版本
	 */
	@ApiModelProperty("版本")
	private String version;

	/**
	 * 可见
	 */
	@ApiModelProperty("是否可见")
	private Boolean visible;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private String createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private String updateTime;

	/**
	 * 装置资源id
	 */
	@ApiModelProperty("设备资源ID")
	private String deviceResourceId = "";

	/**
	 * 家分页
	 */
	@ApiModelProperty("主页")
	private String homePage = "";

	/**
	 * 图像资源id
	 */
	@ApiModelProperty("镜像资源ID")
	private String imageResourceId = "";

	/**
	 * 手动
	 */
	@ApiModelProperty("手册")
	private String manual;

	/**
	 * 场景
	 */
	@ApiModelProperty("场景ID")
	private String scene;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private String sceneData;

	/**
	 * 标记
	 */
	@ApiModelProperty("标签")
	private List<String> tag;

	/**
	 * 禁用
	 */
	@ApiModelProperty("是否禁用")
	private Boolean disabled;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	private String type;

	/**
	 * 内置
	 */
	@ApiModelProperty("是否内置")
	private Boolean builtin;
}

