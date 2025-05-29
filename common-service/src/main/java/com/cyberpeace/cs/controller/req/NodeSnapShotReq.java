package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 节点折拍摄请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `NodeSnapShotReq` 的 Java 类，用于表示节点快照请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，通常用于接收前端传来的参数。 
 * - 它主要用于处理与“节点快照”相关的请求数据。 
 * 2. **依赖的库**： 
 * - 使用了 Lombok 的 `@Getter`, `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 `io.swagger.annotations.ApiModel` 和 `@ApiModelProperty` 注解，用于生成 API 文档。 
 * - 使用了 `javax.validation.constraints.NotBlank` 注解，用于验证字段是否为空或仅包含空白字符。 
 * 3. **字段说明**： 
 * - `sceneId`：场景编号，必填（通过 `@NotBlank` 标记）。 
 * - `nodeId`：节点编号，必填（通过 `@NotBlank` 标记）。 
 * - `snapshotName`：快照名称，必填（通过 `@NotBlank` 标记）。 
 * - `description`：快照描述，非必填。 
 * 4. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，可能用于网络传输或持久化。 
 * 5. **注释**： 
 * - 类和字段都有详细的注释，说明了每个字段的用途，便于维护和理解。 
 * 总结：这是一个简洁且功能明确的请求体类，主要用于接收与节点快照相关的参数，并支持 API 文档生成和字段验证。 
**/
@Getter
@Setter
@ApiModel
public class NodeSnapShotReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景编号")
	@NotBlank
	private String sceneId;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点编号")
	@NotBlank
	private String nodeId;

	/**
	 * 快照名称
	 */
	@ApiModelProperty("快照名称")
	@NotBlank
	private String snapshotName;

	/**
	 * 描述
	 */
	@ApiModelProperty("快照描述")
	private String description;
}

