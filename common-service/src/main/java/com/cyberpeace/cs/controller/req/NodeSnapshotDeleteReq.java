/**
 * 这段代码定义了一个名为 `NodeSnapshotDeleteReq` 的 Java 类，用于表示节点快照删除请求的数据结构。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是用于接收节点快照删除请求的请求体（Request Body），包含场景 ID、节点 ID 和快照 ID。 
 * 2. **注解说明**： 
 * - `@ApiModel`：标记该类为 Swagger API 模型对象，用于生成接口文档。 
 * - `@ApiModelProperty`：为类中的字段提供描述信息，方便生成接口文档时展示字段含义。 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成字段的 getter 和 setter 方法。 
 * - `@NotBlank` 和 `@NotEmpty`：来自 Hibernate Validator，用于验证字段是否符合非空或非空白的要求。 
 * 3. **字段说明**： 
 * - `sceneId`：场景编号，必须是非空字符串。 
 * - `nodeId`：节点编号，必须是非空字符串且不能为空白。 
 * - `snapshotId`：快照编号，必须是非空字符串。 
 * 4. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 5. **设计目的**： 
 * - 该类主要用于接收前端传递的节点快照删除请求数据，并通过注解进行参数校验，确保请求数据的有效性。 
 * 总结：这是一个简洁且功能明确的 Java Bean，用于处理节点快照删除请求，结合了 Lombok 和 Hibernate Validator 提供的便利特性。 
**/
package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 节点快照删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class NodeSnapshotDeleteReq implements Serializable {

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
	@NotEmpty
	private String nodeId;

	/**
	 * 快照id
	 */
	@ApiModelProperty("快照编号")
	@NotBlank
	private String snapshotId;

}
