package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 名称有效请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `NameValidReq` 的 Java 类，位于 `com.cyberpeace.cr.crbase.controller.req` 包中。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，用于接收与名称有效性验证相关的请求数据。 
 * - 它包含三个字段：`name`（名称）、`type`（类型）和 `id`（资源编号）。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `name`：表示名称，类型为字符串，通过 Swagger 注解标记为“模板名称”。 
 * - `type`：表示类型，类型为整数，通过 Swagger 注解标记为“训练类型”。 
 * - `id`：表示资源编号，类型为字符串，通过 Swagger 注解标记为“模板编号”。 
 * 4. **设计目的**： 
 * - 该类主要用于控制器层接收前端传递的请求参数，便于后续处理逻辑使用。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装与名称有效性验证相关的请求参数，并通过注解增强了代码的可读性和文档生成能力。 
**/
@Getter
@Setter
public class NameValidReq {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "模板名称")
	String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "训练类型")
	Integer type;

	/**
	 * id
	 */
	@ApiModelProperty(value = "resource_id，模板编号")
	String id;

}

