/**
 * 该代码定义了一个名为 `TopoData` 的类，用于表示拓扑数据。以下是简要分析： 
 * 1. **类的功能**： 
 * - `TopoData` 类是 `Serializable` 接口的实现者，意味着它可以被序列化，通常用于在网络中传输或持久化存储。 
 * - 它包含两个主要属性：`topology` 和 `plugins`。 
 * 2. **属性说明**： 
 * - `topology`：表示拓扑学相关的数据，具体类型未在代码中展示，但应该是自定义的 `Topology` 类。 
 * - `plugins`：表示场景插件，具体类型为 `ScenePlugins`。 
 * 3. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了属性的 getter 和 setter 方法，减少了冗余代码。 
 * 4. **用途**： 
 * - 该类可能是某个系统中用于管理拓扑数据和相关插件的核心数据结构，可能与场景相关的业务逻辑紧密关联。 
 * 总结：`TopoData` 是一个简单的数据传输对象（DTO），用于封装拓扑学和插件相关的信息，并支持序列化操作。 
**/
package com.cyberpeace.cs.service.context.scene;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 拓扑数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TopoData implements Serializable {

	/**
	 * 拓扑学
	 */
	private Topology topology;

	/**
	 * 插件
	 */
	private ScenePlugins plugins;

}
