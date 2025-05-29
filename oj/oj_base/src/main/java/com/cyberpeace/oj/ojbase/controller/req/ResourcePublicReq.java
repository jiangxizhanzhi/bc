package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源公共请求体
 *
 * @author SN
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ResourcePublicReq` 的 Java 类，该类用于表示资源公共请求体。以下是对其主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.ojbase.controller.req` 
 * - **类名**：`ResourcePublicReq` 
 * - **作用**：表示与资源相关的公共请求体，用于在系统中更新资源的状态或信息。 
 * ### 类属性 
 * - **resourceIds** 
 * - **类型**：`List<String>` 
 * - **注解**： 
 * - `@NotEmpty`：确保列表不为空。 
 * - `@ApiModelProperty(value = "更新资源ID")`：用于Swagger文档生成，说明此字段为更新资源ID。 
 * - **作用**：存储需要操作的资源ID列表。 
 * - **isPublic** 
 * - **类型**：`Boolean` 
 * - **注解**： 
 * - `@NotNull`：确保该字段不为null。 
 * - `@ApiModelProperty(value = "是否公开")`：用于Swagger文档生成，说明此字段为是否公开。 
 * - **作用**：表示资源是否公开。 
 * ### 类特性 
 * - **Getter 和 Setter 方法** 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - **Lombok** 
 * - Lombok 是一个简化Java代码的库，通过注解减少样板代码的编写。在这个类中，使用了 `@Getter` 和 `@Setter` 注解来自动生成getter和setter方法。 
 * - **Validation** 
 * - 使用了 `javax.validation.constraints` 包中的注解 `@NotEmpty` 和 `@NotNull` 来进行数据验证，确保请求参数的有效性。 
 * ### 用途 
 * - **API 请求体**：这个类通常作为RESTful API 请求的一部分，用来接收客户端发送的请求数据。 
 * - **资源管理**：主要用于处理资源的公开状态更新，以及相关资源ID的管理。 
 * ### 总结 
 * `ResourcePublicReq` 类是一个简洁且功能明确的数据传输对象（DTO），用于在系统中处理资源的公开状态更新。它包含了必要的验证逻辑，并通过Lombok减少了样板代码的编写。 
**/
@Getter
@Setter
public class ResourcePublicReq {

	/**
	* 资源id列表
	*
	* @see List<String>
	*/
	  @ApiModelProperty("更新资源ID")
    @NotEmpty
    private List<String> resourceIds;

	/**
	* 是否公开
	*
	* @see Boolean
	*/
	  @ApiModelProperty("是否公开")
    @NotNull
    private Boolean isPublic;
}

