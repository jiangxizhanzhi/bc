/**
 * 这段代码定义了一个名为 `MarkData` 的 Java 类，该类位于包 `com.cyberpeace.integratation.model.evaluation` 中。这个类的主要目的是存储某种标记数据。下面是详细的分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.integratation.model.evaluation; 
 * ``` 
 * - 这行代码指定了类所在的包。 
 * ### 导入语句 
 * ```java 
 * import java.io.Serializable; 
 * import java.util.List; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `Serializable`：这是一个接口，用于实现序列化功能，使得对象可以在网络中传输或保存到文件中。 
 * - `List<MarkItemData>`：表示一个泛型列表，其中包含 `MarkItemData` 类型的对象。 
 * - `@Getter` 和 `@Setter`：这两个注解来自 Lombok 库，用于自动生成 getter 和 setter 方法。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * public class MarkData implements Serializable { 
 * ``` 
 * - `MarkData` 类实现了 `Serializable` 接口，这意味着它可以被序列化。 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 自动生成对应的 getter 和 setter 方法。 
 * ### 成员变量 
 * ```java 
 * private Long baseTime; 
 * private List<MarkItemData> data; 
 * ``` 
 * - `baseTime`：这是一个 `Long` 类型的变量，表示基准时间。 
 * - `data`：这是一个 `List<MarkItemData>` 类型的变量，表示一组标记数据。 
 * ### 文档注释 
 * ```java 
 * /** 
 * * 标记数据 
 * * 
 * * @author SN 
 * * @date 2024/11/08 
 *
 * ``` 
 * - 文档注释说明了这个类的功能是存储标记数据，并且提供了作者和日期信息。 
 * ### 总结 
 * `MarkData` 类主要用于存储与时间相关的标记数据。它有两个主要成员变量：`baseTime` 和 `data`。通过使用 Lombok 库中的 `@Getter` 和 `@Setter` 注解，省去了手动编写 getter 和 setter 方法的繁琐过程。这个类可以被序列化，因此可以方便地在网络中传输或保存到文件中。 
**/
package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 标记数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MarkData implements Serializable {

	/**
	 * 基准时间
	 */
	private Long baseTime;

	/**
	 * 数据
	 */
	List<MarkItemData> data;
}
