/**
 * 这段代码定义了一个名为 `IdAndName` 的 Java 类，位于包 `com.cyberpeace.space.model` 中。以下是代码的主要分析： 
 * 1. **功能描述**： 
 * - 该类主要用于表示一个包含 `id` 和 `name` 属性的对象。 
 * - 注释说明了其用途是提供一个简单的 `id` 和 `名称` 结构。 
 * 2. **依赖与注解**： 
 * - 使用了 Lombok 提供的注解： 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - `@NoArgsConstructor` 和 `@AllArgsConstructor`：分别生成无参构造函数和全参构造函数。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述 API 接口中的字段信息： 
 * - `resourceId` 字段被标记为 `id`。 
 * - `name` 字段被标记为 `名称`，并且注释说明前端不需要传递该字段。 
 * 3. **属性分析**： 
 * - `resourceId`：使用 `String` 类型表示 `id`，而不是默认的 `Integer`，可能是为了兼容某些场景下的字符串 ID。 
 * - `name`：表示名称，注释说明前端无需传递该字段，可能仅用于后端逻辑或展示。 
 * 4. **设计意图**： 
 * - 该类是一个数据传输对象（DTO），通常用于 API 数据交互。 
 * - 使用 Lombok 减少了样板代码，提升了开发效率。 
 * 总结：这是一个简单的 DTO 类，用于传递 `id` 和 `name` 数据，适合在 API 中作为请求或响应的一部分使用。 
**/
package com.cyberpeace.space.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * id和名称
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdAndName {

//	private Integer id;

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "名称, 前端不用传")
	private String name;

}
