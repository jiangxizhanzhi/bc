/**
 * 这段代码定义了一个名为 `MissionCategoryRes` 的 Java 类，用于表示任务类别的响应结果。以下是代码的简要分析： 
 * 1. **包与导入**： 
 * - 该类位于 `com.cyberpeace.cr.crbase.controller.rsp` 包中。 
 * - 导入了 `java.io.Serializable` 接口和 `io.swagger.annotations.ApiModelProperty` 注解。 
 * 2. **类的功能**： 
 * - 这是一个数据传输对象（DTO），用于封装任务类别相关的响应信息。 
 * 3. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述信息，便于 API 文档生成。 
 * 4. **字段说明**： 
 * - `resourceId`：任务类型的唯一标识符，标注为“类型id”。 
 * - `name`：任务类型的名称，标注为“名称”。 
 * - `key`：任务类型的英文名称，标注为“英文名称”。 
 * 5. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化。 
 * 总结：这是一个简洁的数据类，主要用于任务类别信息的序列化和 API 响应。通过 Lombok 和 Swagger 注解，减少了样板代码，提高了开发效率。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务类别结果
 *
 * @author SN@date 2024/11/08
 */
@Getter
@Setter
public class MissionCategoryRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("类型id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 钥匙
	 */
	@ApiModelProperty("英文名称")
	private String key;
}
