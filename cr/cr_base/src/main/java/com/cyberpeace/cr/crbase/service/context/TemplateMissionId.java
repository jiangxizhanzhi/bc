package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 模版任务id
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TemplateMissionId` 的 Java 类，用于表示模板任务的 ID。以下是代码的简要分析： 
 * 1. **包声明**：代码位于 `com.cyberpeace.cr.crbase.service.context` 包中。 
 * 2. **依赖引入**： 
 * - 引入了 `java.io.Serializable` 接口，表明该类可以被序列化。 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 3. **类功能**： 
 * - 类名为 `TemplateMissionId`，用于标识模板任务的唯一 ID。 
 * - 它实现了 `Serializable` 接口，可能用于在不同上下文中传递或存储。 
 * 4. **属性**： 
 * - 类中包含一个私有字符串属性 `resourceId`，用于存储资源 ID。 
 * 5. **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法，简化代码。 
 * 总结：这是一个简单的数据传输对象（DTO），主要用于存储与模板任务相关的资源 ID，并支持序列化操作。 
**/
@Getter
@Setter
public class TemplateMissionId implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;


}

