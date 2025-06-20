package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 节点老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `NodeOpsReq` 的 Java 类，用于表示一个请求体对象。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类是 `com.cyberpeace.cs.controller.req.scene` 包的一部分，主要用于处理与“节点老年退休金计划”相关的请求数据。 
 * 2. **继承与注解**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成 getter 和 setter 方法，减少了冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加了描述信息，便于接口文档生成。 
 * 3. **字段说明**： 
 * - `mpSceneId`：中台场景 ID，用于标识特定的业务场景。 
 * - `nodeId`：节点 ID，用于标识具体的节点。 
 * 4. **设计目的**： 
 * - 该类是一个简单的数据传输对象（DTO），用于封装前端传来的请求参数，便于后端处理。 
 * 总结：这是一个轻量级的数据传输类，通过 Lombok 和 Swagger 注解简化了开发过程，适合用于处理与节点相关的基本请求数据。 
**/
@Getter
@Setter
public class NodeOpsReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 中台场景id
	 */
	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id")
	private String nodeId;
}

