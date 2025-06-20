package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 收集请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CollectionReq` 的 Java 类，用于表示收藏请求的数据模型。以下是对此代码的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.oj.ojbase.controller.req`，表明该类位于项目的特定包中。 
 * - **导入**： 
 * - `java.io.Serializable`：确保类可以序列化。 
 * - `javax.validation.constraints.NotBlank` 和 `javax.validation.constraints.NotNull`：用于验证字段的非空性。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：简化属性的 getter 和 setter 方法的生成。 
 * ### 类与注解 
 * - **类声明**：`@Getter` 和 `@Setter` 注解来自 Lombok，自动生成 getter 和 setter 方法。 
 * - **ApiModel**：使用 Swagger 注解 `@ApiModel` 来描述这个类代表的模型，并提供一个简短的描述“收藏请求”。 
 * ### 属性 
 * - **businessId** 
 * - **类型**：`String` 
 * - **注释**：收藏的资源编号（课程、课时等）。 
 * - **约束**：`@NotBlank` 表示该字段不能为空或空白字符串。 
 * - **isCollection** 
 * - **类型**：`Boolean` 
 * - **注释**：表示是否进行收藏（true 表示收藏，false 表示取消收藏）。 
 * - **约束**：`@NotNull` 表示该字段不能为空。 
 * - **businessType** 
 * - **类型**：`Integer` 
 * - **注释**：资源类型（例如理论题、漏洞题、实操题、课程、课时等）。 
 * - **约束**：`@NotNull` 表示该字段不能为空。 
 * ### 总结 
 * - 这个类主要用于接收收藏请求的数据，包括资源编号、收藏状态和资源类型。 
 * - 使用了 Lombok 来减少样板代码。 
 * - 使用了 Swagger 注解来帮助生成 API 文档。 
 * - 字段都有相应的验证注解以确保数据的有效性。 
**/
@Getter
@Setter
@ApiModel("收藏请求")
public class CollectionReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty("收藏的资源编号 课程、课时")
    @NotBlank
    private String businessId;

    /**
     * is 集合
     *
     * @see Boolean
     */
    @ApiModelProperty("是否进行收藏 true进行收藏，false取消收藏")
    @NotNull
    private Boolean isCollection;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty("资源类型 0=理论题 1=漏洞题(暂时无用) 2=实操题 3=课程 4=课时")
    @NotNull
    private Integer businessType;

}

