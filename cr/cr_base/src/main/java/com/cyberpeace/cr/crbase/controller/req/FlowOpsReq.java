package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 流老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `FlowOpsReq` 的 Java 类，用于表示流老年退休金计划的请求体。以下是代码的主要分析和总结： 
 * ### 主要功能 
 * - **类的作用**：`FlowOpsReq` 是一个数据传输对象（DTO），用于封装流老年退休金计划请求的相关参数。 
 * - **继承关系**：实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化。 
 * ### 关键特性 
 * 1. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，便于生成 API 文档。 
 * 2. **字段说明**： 
 * - `roomId`：房间 ID，对应 Swagger 注解中的“团队训练实例 id”。 
 * - `nodeId`：节点 ID，对应 Swagger 注解中的“节点 id”。 
 * - `flowId`：流 ID，对应 Swagger 注解中的“流量 id”。 
 * ### 总结 
 * - **设计目的**：该类主要用于接收前端传递的请求参数，支持序列化操作。 
 * - **优点**：通过 Lombok 减少了冗余代码，同时结合 Swagger 注解提升了代码的可读性和文档生成的便利性。 
 * - **适用场景**：适用于需要处理流老年退休金计划相关业务的接口开发中。 
**/
@Getter
@Setter
public class FlowOpsReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty("团队训练实例id")
	private String roomId;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	private String nodeId;

	/**
	 * 流id
	 */
	@ApiModelProperty("流量id")
	private String flowId;
}

