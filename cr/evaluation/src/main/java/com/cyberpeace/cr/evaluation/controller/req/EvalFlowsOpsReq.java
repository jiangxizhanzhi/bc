package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.scene.FlowInfoContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估流老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalFlowsOpsReq` 的 Java 类，该类用于表示评估流操作请求的数据结构。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.req` 
 * - **功能**: 用于封装评估流操作请求的数据。 
 * - **继承**: 实现了 `Serializable` 接口，支持序列化。 
 * ### 类属性 
 * - **taskId** (`String`): 任务ID，必填。 
 * - **nodeId** (`String`): 节点ID，必填。 
 * - **runType** (`int`): 执行方式，必填。可选值为 0（串行）或 1（并行）。 
 * - **flows** (`List<FlowInfoContext>`): 流量列表，非必填。 
 * ### 注解 
 * - **@Getter 和 @Setter**: Lombok 提供的注解，自动生成 getter 和 setter 方法。 
 * - **@ApiModelProperty**: Swagger 注解，用于描述 API 文档中的字段信息。 
 * - **@NotNull**: Java Bean Validation 注解，确保字段不为空。 
 * ### 功能描述 
 * - **评估流操作请求**: 该类主要用于封装评估流操作请求的数据，包括任务ID、节点ID、执行方式和流量列表。 
 * - **数据验证**: 使用 `@NotNull` 确保某些字段在请求时必须提供。 
 * ### 总结 
 * 这个类是一个典型的请求对象，用于在控制器层接收客户端发送的评估流操作请求。它通过属性和注解来描述请求的数据结构，并提供了基本的数据验证功能。 
**/
@Getter
@Setter
public class EvalFlowsOpsReq implements Serializable {

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
	 * 运行类型
	 */
	@ApiModelProperty(value = "执行方式：0 串行，1 并行")
	@NotNull
	private int runType;

	/**
	 * 流
	 */
	@ApiModelProperty("流量列表")
	private List<FlowInfoContext> flows;
}

