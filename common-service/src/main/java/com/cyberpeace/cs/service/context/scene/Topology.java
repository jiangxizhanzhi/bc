/**
 * 这段代码定义了一个名为 `Topology` 的类，用于表示拓扑学相关的数据结构。以下是简要分析： 
 * 1. **包与导入**： 
 * - 该类位于 `com.cyberpeace.cs.service.context.scene` 包中。 
 * - 导入了 `java.io.Serializable` 和 `java.util.List`，表明该类实现了序列化接口，并且包含一个泛型列表。 
 * 2. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，减少了冗余代码。 
 * 3. **类功能**： 
 * - 类名 `Topology` 表示这是一个与拓扑学相关的实体类。 
 * - 定义了一个私有属性 `elements`，类型为 `List<TopoElement>`，用于存储拓扑中的元素。 
 * 4. **序列化支持**： 
 * - 实现了 `Serializable` 接口，意味着该类的对象可以被序列化，便于在网络传输或持久化时使用。 
 * 总结：这是一个简单的数据模型类，主要用于存储拓扑学中的元素集合，具备良好的封装性和序列化能力。 
**/
package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 拓扑学
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class Topology implements Serializable {

	/**
	 * 元素
	 */
	private List<TopoElement> elements;
}
