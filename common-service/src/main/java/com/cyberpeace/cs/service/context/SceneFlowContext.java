package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景流上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneFlowContext` 的 Java 类，用于表示场景流上下文。以下是对其简要分析： 
 * ### 功能描述： 
 * `SceneFlowContext` 是一个数据传输对象（DTO），主要用于存储与场景流相关的上下文信息。它包含了流量的基本属性以及关联的节点信息。 
 * --- 
 * ### 关键特性： 
 * 1. **继承与实现**： 
 * - 实现了 `Serializable` 接口，表明它可以被序列化，通常用于跨进程传递或持久化。 
 * 2. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter`, `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供 API 文档描述。 
 * 3. **字段说明**： 
 * - `flowId`: 唯一流量 ID，通过 `@ApiModelProperty` 注解标明其含义。 
 * - `interactiveType`: 表示交互类型，取值范围为 `{0, 1, 2}`，分别对应背景流量、攻击流量和全流量。 
 * - `nodeIds`: 包含该流量的所有节点 ID，默认初始化为一个空列表。 
 * 4. **构造方法**： 
 * - 提供了两个构造方法： 
 * - 带参构造方法：用于初始化 `flowId` 和 `interactiveType`。 
 * - 无参构造方法：用于框架默认实例化。 
 * 5. **工具类引用**： 
 * - 使用了 Apache Commons Compress 提供的 `Lists.newArrayList()` 方法来初始化 `nodeIds` 列表。 
 * --- 
 * ### 总结： 
 * 该类是一个典型的 DTO，封装了与场景流相关的上下文信息，并提供了必要的字段和方法。代码结构清晰，注解丰富，便于后续扩展和维护。 
**/
@Getter
@Setter
public class SceneFlowContext implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 流id
	 */
	@ApiModelProperty("流量Id")
	private String flowId;

	/**
	 * 交互式类型
	 */
	@ApiModelProperty("交互类型：0 背景流量，1 攻击流量，2 全流量")
	private Integer interactiveType;

	/**
	 * 节点id列表
	 */
	@ApiModelProperty("包含该流量的节点列表")
	private List<String> nodeIds = Lists.newArrayList();

	public SceneFlowContext(String flowId, Integer interactiveType) {
		this.flowId = flowId;
		this.interactiveType = interactiveType;
	}

	public SceneFlowContext() {}

}

