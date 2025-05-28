/**
 * 这段代码定义了一个名为 `SceneStatus` 的 Java 类，用于表示场景的状态信息。以下是简要分析： 
 * 1. **类的功能**： 
 * `SceneStatus` 是一个场景状态的实体类，主要用于存储与场景相关的事件及其状态。 
 * 2. **依赖与注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于在分布式系统中传输或持久化。 
 * 3. **字段说明**： 
 * - `event`：表示场景中的某个事件，类型为 `Integer`。 
 * - `eventStatus`：表示事件的状态，类型为 `Integer`。 
 * 4. **适用场景**： 
 * 此类可能用于游戏、模拟系统或其他需要跟踪场景内事件状态的应用场景。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装场景中事件及其状态的信息。 
**/
package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景状态
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneStatus implements Serializable {

	/**
	 * 事件
	 */
	private Integer event;

	/**
	 * 事件状态
	 */
	private Integer eventStatus;
}
