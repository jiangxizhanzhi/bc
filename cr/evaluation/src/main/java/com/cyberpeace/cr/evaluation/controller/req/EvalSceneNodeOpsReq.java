package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估场景节点老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSceneNodeOpsReq` 的 Java 类，该类用于表示评估场景节点操作的请求体。以下是对其主要特性和功能的简要分析： 
 * ### 包声明与导入 
 * - 该类位于 `com.cyberpeace.cr.evaluation.controller.req` 包中。 
 * - 导入了必要的库，包括 `java.io.Serializable` 接口、`javax.validation.constraints.NotBlank` 注解以及 Swagger 注解 `io.swagger.annotations.ApiModelProperty`。 
 * ### 类声明 
 * - 类 `EvalSceneNodeOpsReq` 使用 Lombok 提供的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 类实现了 `Serializable` 接口，这意味着它可以被序列化（通常用于网络传输或存储）。 
 * ### 字段 
 * - `taskId`：任务ID，使用 `@ApiModelProperty` 注解标明其在 API 文档中的描述，并使用 `@NotBlank` 注解确保该字段不为空白字符串。 
 * - `nodeId`：节点ID，同样使用 `@ApiModelProperty` 注解标明其描述，并使用 `@NotBlank` 注解确保该字段不为空白字符串。 
 * ### 总结 
 * 这个类主要用于接收评估场景节点操作的请求参数，具体来说是任务ID和节点ID。这些信息通常用于识别特定的任务和节点，以便进行相关的评估操作。通过使用 Lombok 注解，简化了代码的编写，并且通过引入验证注解确保了数据的有效性。 
**/
@Getter
@Setter
public class EvalSceneNodeOpsReq implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "测评靶场任务id", required = true)
	@NotBlank
	private String taskId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}

