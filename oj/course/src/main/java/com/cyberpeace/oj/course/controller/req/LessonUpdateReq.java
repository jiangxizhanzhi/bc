package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 课更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see LessonAddReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonUpdateReq` 的 Java 类，该类用于表示课程更新请求的数据模型。以下是对该代码的简要分析： 
 * ### 类定义和继承关系 
 * - `LessonUpdateReq` 类继承自 `LessonAddReq` 类。 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * ### 导入的依赖 
 * - `com.cyberpeace.common.baseinterface.Update`：一个接口，可能用于标识某些验证组。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档生成。 
 * - `javax.validation.constraints.NotBlank`：用于验证字段是否为空或仅包含空白字符。 
 * ### 类注释 
 * - `@ApiModel` 注解用于描述该类在 Swagger 中的文档信息。 
 * - 类注释说明这是一个用于课程更新的请求体，并指出了相关类和版本信息。 
 * ### 字段 
 * - `resourceId`：资源 ID，使用 `@NotBlank` 注解标记，表示该字段在更新操作时必须提供非空值（属于 `Update` 验证组）。 
 * - `handoutUrl`：讲义 URL，用于存储讲义文件的链接。 
 * - `videoUrl`：视频 URL，用于存储视频文件的链接。 
 * ### Getter 和 Setter 方法 
 * - 自定义了 `setResource_id`、`setHandout_url` 和 `setVideo_url` 方法，这些方法与字段名称不完全匹配（JavaBean 规范建议方法名应与字段名对应）。这可能会导致一些混淆或潜在的问题。 
 * ### 总结 
 * - `LessonUpdateReq` 类是一个数据传输对象（DTO），用于封装课程更新请求中的数据。 
 * - 它继承自 `LessonAddReq` 类，表明它可能共享一些通用字段。 
 * - 通过 Swagger 注解，该类可以在 API 文档中自动生成描述。 
 * - 需要注意的是，自定义的 setter 方法名称不符合 JavaBean 规范，建议统一使用标准命名规则以避免混淆。 
**/
@Getter
@Setter
@ApiModel
public class LessonUpdateReq extends LessonAddReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	/**
	 * 讲义 URL
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "handout_url", value = "讲义类型")
	private String handoutUrl;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "video_url", value = "视频地址")
	private String videoUrl;

	public void setResource_id(String resource_id) {
		this.resourceId = resource_id;
	}

	public void setHandout_url(String handoutUrl) {
		this.handoutUrl = handoutUrl;
	}

	public void setVideo_url(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}

