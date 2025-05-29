package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板简要信息
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `AddTemplateMission` 的 Java 类，用于表示任务模板的简要信息。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 这是一个简单的 Java Bean 类，主要用于存储任务模板的相关信息。 
 * - 它实现了 `Serializable` 接口，表明该类的对象可以被序列化（通常用于网络传输或持久化）。 
 * 2. **字段说明**： 
 * - `resourceId`：任务的唯一标识符，用于关联具体的资源。 
 * - `title`：任务的标题，简要描述任务内容。 
 * - `description`：任务的详细描述，提供更丰富的信息。 
 * 3. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，减少冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述性注释，便于 API 文档生成。 
 * 4. **设计特点**： 
 * - 类的设计简单明了，专注于存储任务模板的基本信息。 
 * - 符合面向对象编程的原则，通过封装实现数据的管理。 
 * 总结：这是一个轻量级的数据传输对象（DTO），用于在系统中传递任务模板的基本信息，具有良好的可读性和扩展性。 
**/
@Getter
@Setter
public class AddTemplateMission implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "任务标题")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "任务描述")
	private String description;

}

