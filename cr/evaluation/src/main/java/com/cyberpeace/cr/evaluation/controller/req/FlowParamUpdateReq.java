package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 流参数更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `FlowParamUpdateReq` 的 Java 类，用于表示更新流参数的请求体。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - **类名**：`FlowParamUpdateReq` 
 * - **继承关系**：实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：为所有字段生成 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModelProperty`：用于描述字段在 Swagger 文档中的展示信息。 
 * ### 字段 
 * 1. **taskId** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty("测评靶场任务id")` 
 * - `@NotNull`：表示该字段不能为空。 
 * - **描述**：任务ID。 
 * 2. **nodeId** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty("节点id")` 
 * - `@NotNull`：表示该字段不能为空。 
 * - **描述**：节点ID。 
 * 3. **flowId** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "流量id")` 
 * - `@NotNull`：表示该字段不能为空。 
 * - **描述**：流ID。 
 * 4. **sourceIp** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "源ip")` 
 * - **描述**：源IP地址。 
 * ### 总结 
 * - 这个类主要用于接收更新流参数的请求数据。 
 * - 所有字段都是必填项（除了 `sourceIp`）。 
 * - 使用 Lombok 注解自动生成了 getter 和 setter 方法。 
 * - 使用 Swagger 注解来生成 API 文档的描述信息。 
 * 这个类的设计简洁明了，适用于接收前端传来的更新流参数的数据。 
**/
@Getter
@Setter
public class FlowParamUpdateReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 任务id
	 */
	@ApiModelProperty("测评靶场任务id")
	@NotNull
	private String taskId;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	@NotNull
	private String nodeId;

	/**
	 * 流id
	 */
	@ApiModelProperty(value = "流量id")
	@NotNull
	private String flowId;

	/**
	 * 源ip地址
	 */
	@ApiModelProperty(value = "源ip")
	private String sourceIp;
}

