package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 流更新上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `FlowUpdateContext` 的 Java 类，用于表示流更新的上下文信息。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个数据传输对象（DTO），主要用于封装流更新相关的上下文信息。 
 * 2. **属性**： 
 * - `mpSceneId`：中台场景ID，用于标识特定的业务场景。 
 * - `nodeId`：节点ID，表示某个具体的节点。 
 * - `flowIds`：流ID列表，存储与当前上下文相关的多个流ID。 
 * 3. **构造方法**： 
 * - 提供了两个构造方法： 
 * - 带参数的构造方法：用于初始化 `mpSceneId`、`nodeId` 和 `flowIds`。 
 * - 无参构造方法：用于框架或工具在反射时使用。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的getter和setter方法。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，可能用于网络传输或持久化。 
 * 5. **用途**： 
 * - 该类通常用于在服务层或控制器层传递流更新的相关信息。 
 * 总结：这是一个简单的 DTO 类，用于封装流更新的上下文信息，支持序列化，并提供了必要的构造方法和属性访问功能。 
**/
@Getter
@Setter
public class FlowUpdateContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 中台场景id
	 */
	private String mpSceneId;

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 流id列表
	 */
	private List<String> flowIds;

	public FlowUpdateContext(String mpSceneId, String nodeId, List<String> flowIds) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.flowIds = flowIds;
	}

	public FlowUpdateContext() {}

}

