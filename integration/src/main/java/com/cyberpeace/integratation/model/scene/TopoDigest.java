package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 拓扑摘要数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TopoDigest` 的 Java 类，该类实现了 `Serializable` 接口。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.integratation.model.scene` 
 * - **类名**：`TopoDigest` 
 * - **实现接口**：`Serializable` 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * 1. **nodeCounts** 
 * - **类型**：`Integer` 
 * - **描述**：拓扑中的节点数量。 
 * 2. **resourceOccupiedNums** 
 * - **类型**：`Integer` 
 * - **描述**：拓扑中被占用的授权节点数量。 
 * 3. **topoId** 
 * - **类型**：`String` 
 * - **描述**：拓扑的唯一标识符（ID）。 
 * ### 总结 
 * - **用途**：`TopoDigest` 类用于表示拓扑的摘要信息，包括节点数量、占用的授权节点数量以及拓扑的 ID。 
 * - **序列化**：通过实现 `Serializable` 接口，该类可以被序列化，便于在网络传输或持久化存储时使用。 
 * 这个类的设计简洁明了，适合用于存储和传递拓扑的基本摘要信息。 
**/
@Getter
@Setter
public class TopoDigest implements Serializable {

	/**
	 * 拓扑中节点数
	 */
	private Integer nodeCounts;

	/**
	 * 拓扑中占用的授权节点数量
	 */
	private Integer resourceOccupiedNums;

	/**
	 * 拓扑id
	 */
	private String topoId;
}

