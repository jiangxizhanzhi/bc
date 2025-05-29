package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景收集
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneCollection` 的 Java 类，用于场景收集相关的功能。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于在网络传输或持久化存储中使用。 
 * - 它主要用于场景数据的收集，包含两个布尔类型的属性。 
 * 2. **属性说明**： 
 * - `enableFlowCollect`：默认值为 `false`，表示是否启用 Flow 数据收集。 
 * - `enableProbeCollect`：未设置默认值，表示是否启用 Probe 数据收集。 
 * 3. **注解与封装**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码。 
 * - 类通过 Lombok 的 `@Data` 注解（隐含了 `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode` 等功能），进一步增强了类的便捷性。 
 * 4. **设计意图**： 
 * - 该类的设计目的是提供一个简单的配置类，用于控制不同类型的场景数据收集功能是否开启。 
 * 总结：这是一个简单的、可序列化的配置类，用于管理场景数据收集功能的开关状态。 
**/
@Getter
@Setter
public class SceneCollection implements Serializable {

	/**
	 * 启用 Flow collect
	 */
	private Boolean enableFlowCollect = false;

	/**
	 * 启用 Probe Collect
	 */
	private Boolean enableProbeCollect;
}

