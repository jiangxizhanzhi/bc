package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;

/**
 * 镜像快照上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `NodeSnapshotContext` 的 Java 类，该类继承自 `SceneOpsContext`。以下是详细的分析和总结： 
 * ### 类和继承关系 
 * - **类名**: `NodeSnapshotContext` 
 * - **父类**: `SceneOpsContext` 
 * - **包**: `com.cyberpeace.integratation.model.scene` 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - `nodeId`: 节点ID，类型为 `String`。 
 * - `snapshotName`: 快照名称，类型为 `String`。 
 * - `description`: 描述信息，类型为 `String`。 
 * ### 用途 
 * - 这个类用于表示与镜像快照相关的上下文信息。具体来说，它包含了节点ID、快照名称和描述信息。 
 * ### 总结 
 * - `NodeSnapshotContext` 是一个数据传输对象（DTO），用于封装与镜像快照相关的上下文信息。 
 * - 它继承自 `SceneOpsContext`，可能包含一些父类中定义的通用操作或属性。 
 * - 使用 Lombok 的注解简化了代码，减少了手动编写 getter 和 setter 方法的工作量。 
 * 这个类的设计目的是为了方便管理和传递镜像快照相关的数据。 
**/
@Getter
@Setter
public class NodeSnapshotContext extends SceneOpsContext {

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 快照名称
	 */
	private String snapshotName;

	/**
	 * 描述
	 */
	private String description;

}

