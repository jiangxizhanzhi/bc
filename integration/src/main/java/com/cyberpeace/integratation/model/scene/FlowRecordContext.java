/**
 * 这段代码定义了一个名为 `FlowRecordContext` 的类，该类继承自 `NodeOpsContext`。这个类主要用于存储与流记录相关的上下文信息。以下是对代码的简要分析： 
 * ### 类定义 
 * - **类名**：`FlowRecordContext` 
 * - **继承**：`NodeOpsContext` 
 * ### 类成员变量 
 * - **flow_id**：字符串类型，表示流的唯一标识符。 
 * - **is_batch**：布尔类型，默认值为 `true`，表示是否为批量处理。 
 * ### Getter 和 Setter 方法 
 * - **setFlowId**：设置流的唯一标识符。 
 * - **setIsBatch**：设置是否为批量处理。 
 * ### 构造方法 
 * - **带参数的构造方法**：接受三个参数 (`mpSceneId`, `nodeId`, `flowId`) 并初始化父类的 `setId` 和 `setNodeId` 方法，以及当前类的 `setFlowId` 方法。 
 * - **无参构造方法**：默认构造方法。 
 * ### 注解 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法，简化代码。 
 * ### 总结 
 * - `FlowRecordContext` 类用于封装与流记录相关的上下文信息。 
 * - 通过继承 `NodeOpsContext` 类，可以复用一些节点操作的上下文信息。 
 * - 类中的字段和方法提供了对流记录的基本操作，如设置和获取流 ID 以及是否为批量处理。 
 * - 使用 Lombok 注解减少了样板代码，使类更简洁易读。 
**/
package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;

/**
 * 流记录上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowRecordContext extends NodeOpsContext {

	/**
	 * 流id
	 */
	private String flow_id;

	/**
	 * 是批量
	 */
	private Boolean is_batch = true;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setIsBatch(Boolean isBatch) {
		this.is_batch = isBatch;
	}

	public FlowRecordContext(String mpSceneId, String nodeId, String flowId) {
		this.setId(mpSceneId);
		this.setNodeId(nodeId);
		this.setFlowId(flowId);
	}

	public FlowRecordContext() {}

}
