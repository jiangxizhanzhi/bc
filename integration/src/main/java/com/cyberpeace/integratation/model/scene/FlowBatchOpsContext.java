/**
 * 这段代码定义了一个名为 `FlowBatchOpsContext` 的 Java 类，该类继承自 `NodeOpsContext`。这个类的主要目的是作为流批量操作的上下文，用于处理与运行类型和流相关的数据。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.integratation.model.scene` 
 * - **类名**：`FlowBatchOpsContext` 
 * - **继承关系**：继承自 `NodeOpsContext` 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **run_type**：一个整数类型的属性，表示运行类型。 
 * - **flows**：一个 `List<FlowInfo>` 类型的属性，表示流列表。 
 * ### 构造函数 
 * - **无参构造函数**：默认构造函数，不带参数。 
 * - **有参构造函数**： 
 * - 参数包括 `String mpSceneId`, `String nodeId`, `Integer runType`。 
 * - 初始化 `run_type`, `nodeId`, 和 `id` 属性。 
 * ### 方法 
 * - **setRunType**：一个公共方法，用于设置 `run_type` 属性。 
 * ### 总结 
 * - `FlowBatchOpsContext` 类主要用于存储和管理流批量操作的上下文信息，如运行类型和流列表。 
 * - 通过继承 `NodeOpsContext` 类，它可能包含一些节点操作的通用逻辑。 
 * - 使用 Lombok 注解简化了属性的访问器生成。 
 * - 提供了初始化上下文信息的构造函数，方便在创建对象时进行必要的配置。 
 * 这个类的设计目的是为了便于管理和传递流批量操作所需的信息。 
**/
package com.cyberpeace.integratation.model.scene;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 流批量老年 退休金 计划上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowBatchOpsContext extends NodeOpsContext {

	/**
	 * 运行类型
	 */
	private Integer run_type;

	/**
	 * 流
	 */
	private List<FlowInfo> flows;

	public void setRunType(Integer runType) {
		this.run_type = runType;
	}

	public FlowBatchOpsContext(String mpSceneId, String nodeId, Integer runType) {

		this.setRunType(runType);

		this.setNodeId(nodeId);

		this.setId(mpSceneId);

	}

	public FlowBatchOpsContext() {}
}
