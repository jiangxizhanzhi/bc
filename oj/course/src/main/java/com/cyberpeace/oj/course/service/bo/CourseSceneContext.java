/**
 * 这段代码定义了一个名为 `CourseSceneContext` 的 Java 类，该类用于表示课程场景的上下文。以下是详细的分析和总结： 
 * ### 包声明和导入 
 * - `package com.cyberpeace.oj.course.service.bo;`：指定了这个类所在的包。 
 * - 导入了 `Serializable` 接口和其他相关类。 
 * ### 类声明 
 * - `@Getter`, `@Setter`, `@NoArgsConstructor` 注解来自 Lombok 库，用于自动生成 getter 和 setter 方法以及无参构造函数。 
 * - `public class CourseSceneContext implements Serializable`：定义了一个公共类 `CourseSceneContext`，该类实现了 `Serializable` 接口，使得对象可以序列化。 
 * ### 成员变量 
 * - `private List<OjLocalScene> scenes;`：存储场景列表，类型为 `OjLocalScene`。 
 * - `private List<String> topos;`：存储拓扑信息，类型为字符串列表。 
 * ### 构造函数 
 * - `public CourseSceneContext(List<OjLocalScene> scenes, List<String> topos)`：自定义构造函数，用于初始化 `scenes` 和 `topos` 列表。 
 * ### 注释 
 * - 类和成员变量都有详细的注释，解释了它们的用途。 
 * ### 总结 
 * - `CourseSceneContext` 类的主要作用是封装课程场景的信息，包括场景列表和拓扑信息。 
 * - 通过 Lombok 注解简化了代码，减少了样板代码。 
 * - 这个类实现了 `Serializable` 接口，方便在需要时进行序列化操作。 
 * 总体来说，这是一个简单且功能明确的类，用于处理课程场景相关的数据。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 课程场景上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CourseSceneContext implements Serializable {

	public CourseSceneContext(List<OjLocalScene> scenes, List<String> topos) {
		this.scenes = scenes;
		this.topos = topos;
	}

	/**
	 * 场景
	 *
	 * @see List<OjLocalScene>
	 */
	private List<OjLocalScene> scenes;

	/**
	 * 拓扑
	 *
	 * @see List<String>
	 */
	private List<String> topos;

}
