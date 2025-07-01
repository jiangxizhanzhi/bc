package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 工具新增请求体
 *
 * @author SN
 * @date 2024/11/08
 * 这段代码定义了一个名为 `ToolAddReq` 的 Java 类，用于表示向系统中添加新工具时的请求体。以下是对该代码的简要分析：
 * ### 包声明和导入
 * - **包声明**：`com.cyberpeace.cr.tool.controller.req`
 * - **导入的库**：
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 用于生成 API 文档。
 * - `lombok.Getter` 和 `lombok.Setter` 用于自动生成 getter 和 setter 方法。
 * - `org.hibernate.validator.constraints.Length` 用于验证字段长度。
 * - `javax.validation.constraints.NotBlank` 用于验证字段不能为空白。
 * ### 类定义
 * - **类名**：`ToolAddReq`
 * - **继承关系**：实现了 `Serializable` 接口，说明该类的对象可以序列化。
 * - **注解**：
 * - `@ApiModel`：用于描述这个类在 API 中的用途。
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。
 * ### 字段定义
 * - `name`：工具的名称，不能为空（使用 `@NotBlank` 验证）。
 * - `category`：工具的类别，不能为空且长度不能超过 255 个字符（使用 `@NotBlank` 和 `@Length` 验证）。
 * - `cover`：工具的封面图片。
 * - `cracked`：表示工具是否已破解。
 * - `disabled`：表示工具是否被禁用。
 * - `language`：工具支持的语言，不能为空（使用 `@NotBlank` 验证）。
 * - `platforms`：工具支持的平台，不能为空（使用 `@NotBlank` 验证）。
 * - `version`：工具的版本号。
 * - `introduction`：工具的介绍。
 * - `files`：工具的附件文件路径，不能为空（使用 `@NotBlank` 验证）。
 * - `imageResourceId`：工具图像资源的 ID。
 * - `type`：工具的类型，包括 `probe`、`attack`、`control` 和 `defense`。
 * - `tag`：工具的标签列表。
 * - `toolPublic`：表示工具是否公开，默认值为 `true`。
 * - `visible`：表示工具是否可见，默认值为 `true`。
 * ### 总结
 * - `ToolAddReq` 类主要用于接收添加新工具的请求参数，并通过各种注解确保这些参数的有效性。
 * - 该类通过 Lombok 注解简化了代码，减少了样板代码。
 * - 使用 Swagger 注解便于生成 API 文档。
**/
@Getter
@Setter
@ApiModel
public class ToolAddReq implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	@NotBlank
	private String name;

	/**
	 * 类别
	 */
	@ApiModelProperty("分类")
	@NotBlank
	@Length(max = 255, message = CrBusinessErrorCode.MAX_7_CATEGORIES)
	private String category;

	/**
	 * 盖
	 */
	@ApiModelProperty("封面")
	private String cover;

	/**
	 * 破获
	 */
	@ApiModelProperty("是否破解")
	private Boolean cracked;

	/**
	 * 禁用
	 */
	@ApiModelProperty("是否禁用")
	private Boolean disabled;

	/**
	 * 语言
	 */
	@ApiModelProperty("语言")
	@NotBlank
	private String language;

	/**
	 * 平台
	 */
	@ApiModelProperty("平台")
	@NotBlank
	private String platforms;

	/**
	 * 版本
	 */
	@ApiModelProperty("版本")
	private String version;

	/**
	 * 介绍
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	/**
	 * 文件
	 */
	@ApiModelProperty("工具附件")
	@NotBlank
	private String files;

	/**
	 * 图像资源id
	 */
	@ApiModelProperty("靶标ID")
	private String imageResourceId;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	private String type;

	/**
	 * 标记
	 */
	@ApiModelProperty("标签")
	private List<String> tag;

	/**
	 * 工具 public
	 */
	private Boolean toolPublic = true;

	private Boolean visible = true;

	public void validate() {
		if (!StringUtils.isEmpty(this.name)){
			CrCommonValidator.validateNameLen(this.name,  CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (StringUtils.isNotBlank(this.introduction)){
			CrCommonValidator.validateLength(this.introduction, 1, 960,
					CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (!CollectionUtils.isEmpty(this.tag)){
			CrCommonValidator.validateListMaxSize(this.tag,0,8, CrBusinessErrorCode.INVALID_TAGS_NUM);
		}
	}
}

