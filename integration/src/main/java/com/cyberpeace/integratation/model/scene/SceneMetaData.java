package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 场景元数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneMetaData` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示场景的元数据，并且使用了 Lombok 库来自动生成 getter 和 setter 方法。 
 * ### 主要特点： 
 * 1. **包声明**：代码位于 `com.cyberpeace.integratation.model.scene` 包中。 
 * 2. **导入的库**：导入了 `Serializable` 接口和 Lombok 的 `Getter` 和 `Setter` 注解。 
 * 3. **注释**：类上有 Javadoc 注释，说明这是一个场景元数据类。 
 * 4. **字段**： 
 * - `businessInstanceId`：表示业务数据编号。 
 * - `sceneName`：表示场景名称。 
 * 5. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * ### 总结： 
 * - `SceneMetaData` 是一个简单的数据传输对象（DTO），用于存储与场景相关的元数据信息。 
 * - 使用 Lombok 可以减少样板代码，提高开发效率。 
 * - 实现 `Serializable` 接口意味着该类的对象可以被序列化，便于在网络上传输或保存到文件中。 
**/
@Getter
@Setter
public class SceneMetaData implements Serializable {

	/**
	 * 业务数据编号
	 */
	private String businessInstanceId;

	/**
	 * 场景名称
	 */
	private String sceneName;

}

