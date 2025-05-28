/**
 * 这段代码定义了一个名为 `SceneDumpContext` 的 Java 类，该类位于 `com.cyberpeace.integratation.model.scene` 包中。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`package com.cyberpeace.integratation.model.scene;` 
 * - 这行代码指定了类所在的包。 
 * - **导入语句**： 
 * ```java 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - 导入了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，用于自动生成属性的 getter 和 setter 方法。 
 * - 导入了 `Serializable` 接口，表示该类可以被序列化。 
 * - **类声明**： 
 * ```java 
 * @Getter 
 * @Setter 
 * public class SceneDumpContext implements Serializable { 
 * ``` 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 类实现了 `Serializable` 接口，使得该对象可以在网络上传输或持久化存储。 
 * ### 属性 
 * - **id**：`private String id;` 
 * - 表示场景的唯一标识符。 
 * - **env_path**：`private String env_path;` 
 * - 表示环境路径。 
 * - **image_path**：`private String image_path;` 
 * - 表示图像路径。 
 * - **single_image_path**：`private String single_image_path;` 
 * - 表示单个图像路径。 
 * - **scene_number**：`private String scene_number;` 
 * - 表示场景的数量。 
 * - **disable_image_export**：`private Boolean disable_image_export;` 
 * - 表示是否禁用图像导出。 
 * ### 文档注释 
 * - 类的文档注释： 
 * ```java 
 * /** 
 * * 场景转 储上下文 
 * * 
 * * @author SN 
 * * @date 2024/11/08 
 * */ 
 * ``` 
 * - 描述了类的作用，并标注了作者和日期。 
 * ### 总结 
 * - `SceneDumpContext` 类主要用于存储与场景相关的上下文信息。 
 * - 通过 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码，减少了样板代码。 
 * - 类实现了 `Serializable` 接口，使其可以被序列化。 
 * - 类中的属性涵盖了场景的基本信息，包括唯一标识符、路径信息、场景数量以及是否禁用图像导出等。 
**/
package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 场景转 储上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneDumpContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * env 路径
	 */
	private String env_path;

	/**
	 * 图像路径
	 */
	private String image_path;

	/**
	 * 单个图像路径
	 */
	private String single_image_path;

	/**
	 * 场景数
	 */
	private String scene_number;

	/**
	 * 禁用图像导出
	 */
	private Boolean disable_image_export;
}