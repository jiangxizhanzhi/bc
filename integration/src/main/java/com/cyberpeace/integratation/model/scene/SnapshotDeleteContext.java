package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;

/**
 * 快照删除上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SnapshotDeleteContext` 的 Java 类，该类是 `SceneOpsContext` 类的子类。这个类的主要目的是提供一个用于删除快照操作的上下文对象。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.integratation.model.scene` 
 * - **类名**：`SnapshotDeleteContext` 
 * - **继承关系**：继承自 `SceneOpsContext` 
 * ### 注解 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **nodeId**：表示节点 ID，类型为 `String`。 
 * - **snapshotId**：表示快照 ID，类型为 `String`。 
 * ### 文档注释 
 * - 类级别的文档注释描述了这是一个用于快照删除操作的上下文类。 
 * - 方法级别的文档注释描述了每个属性的用途。 
 * ### 总结 
 * `SnapshotDeleteContext` 类是一个简单的数据传输对象（DTO），主要用于在删除快照的操作中传递必要的信息（如节点 ID 和快照 ID）。通过继承 `SceneOpsContext` 类，它可能与其他场景操作共享一些通用的上下文信息。 
**/
@Getter
@Setter
public class SnapshotDeleteContext extends SceneOpsContext {

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 快照id
	 */
	private String snapshotId;

}

