package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 类别新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CategoryAddReq` 的 Java 类，用于处理类别新增请求。以下是对其简要而准确的分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.tool.controller.req` 
 * - **类名**: `CategoryAddReq` 
 * - **功能**: 该类用于接收类别新增请求的数据。 
 * ### 主要特性 
 * - **序列化**: 该类实现了 `Serializable` 接口，表明它可以被序列化。 
 * - **Lombok 注解**: 
 * - `@Getter`: 自动生成所有字段的 getter 方法。 
 * - `@Setter`: 自动生成所有字段的 setter 方法。 
 * - **Swagger 注解**: 
 * - `@ApiModel`: 描述该类为一个模型。 
 * - `@ApiModelProperty`: 为模型属性添加描述。 
 * ### 字段 
 * - **cnName** (String): 
 * - **注释**: 中文名称。 
 * - **约束**: 必填（使用 `@NotBlank` 注解）。 
 * - **描述**: 使用 `@ApiModelProperty` 注解进行了描述。 
 * - **type** (String): 
 * - **注释**: 类型，取值范围包括 "probe"（探测类）、"attack"（攻击类）、"control"（控制类）和 "defense"（防御类）。 
 * - **约束**: 必填（使用 `@NotBlank` 注解）。 
 * - **描述**: 使用 `@ApiModelProperty` 注解进行了描述。 
 * ### 总结 
 * `CategoryAddReq` 类是一个简单的数据传输对象（DTO），主要用于接收类别新增请求的数据。它包含了两个必填字段：`cnName` 和 `type`。通过使用 Lombok 和 Swagger 注解，简化了代码并增强了文档描述。 
**/
@Getter
@Setter
@ApiModel
public class CategoryAddReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 中文名称
	 */
	@ApiModelProperty("中文名称")
	@NotBlank
	private String cnName;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	@NotBlank
	private String type;
}

