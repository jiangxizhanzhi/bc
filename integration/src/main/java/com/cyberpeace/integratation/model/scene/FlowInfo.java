/**
 * 这段代码定义了一个名为 `FlowInfo` 的 Java 类，该类实现了 `Serializable` 接口，并使用了 Lombok 库来简化 getter 和 setter 方法的生成。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.integratation.model.scene` 
 * - **类名**：`FlowInfo` 
 * - **实现接口**：`Serializable`，这意味着这个类的对象可以被序列化（即转换为字节流，便于存储或传输）。 
 * ### 字段 
 * - `flow_id`：表示流程的唯一标识符。 
 * - `source_ip`：表示源 IP 地址。 
 * ### Getter 和 Setter 方法 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 自动生成了这两个字段的 getter 和 setter 方法。 
 * - 除此之外，还手动定义了几个方法： 
 * - `setFlowId(String flowId)`：设置 `flow_id`。 
 * - `setId(String flowId)`：设置 `flow_id`（冗余，建议删除）。 
 * - `setSourceIp(String sourceIp)`：设置 `source_ip`。 
 * ### 问题和改进建议 
 * 1. **冗余方法**：`setId(String flowId)` 是多余的，建议删除。 
 * 2. **命名规范**：建议统一使用 `setFlowId` 或 `setFlowId`，避免混淆。 
 * ### 总结 
 * `FlowInfo` 类用于存储与流程相关的简单信息，如流程 ID 和源 IP 地址。通过 Lombok 的注解，代码更加简洁且易于维护。建议删除冗余的方法并保持命名一致性。 
**/
package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 流程信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowInfo implements Serializable {

	/**
	 * 流id
	 */
	private String flow_id;

	/**
	 * 源ip地址
	 */
	private String source_ip;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setId(String flowId) {
		this.flow_id = flowId;
	}

	public void setSourceIp(String sourceIp) {
		this.source_ip = sourceIp;
	}
}
