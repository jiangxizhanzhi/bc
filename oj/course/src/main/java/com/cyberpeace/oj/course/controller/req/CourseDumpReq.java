package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 课程转 储请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseDumpReq` 的 Java 类，该类用于处理课程转储请求。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - 这个类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * - **导入的类** 
 * - `io.swagger.annotations.ApiModel`: 用于描述一个模型（通常是一个数据传输对象）。 
 * - `io.swagger.annotations.ApiModelProperty`: 用于描述模型属性。 
 * - `lombok.Getter`, `lombok.Setter`: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`: Java 接口，表示该类可以序列化。 
 * - **继承关系**：`CourseDumpReq` 实现了 `Serializable` 接口。 
 * ### 类注释 
 * - 该类被标记为 `@ApiModel`，说明这是一个 API 模型类，通常用于 RESTful API 中的数据传输对象（DTO）。 
 * ### 属性 
 * - **resourceIds**: 
 * - 类型: `List<String>` 
 * - 注释: `@ApiModelProperty("课程编号")` 
 * - 描述: 这个属性存储了一组课程的编号（资源 ID 列表）。 
 * ### Getter 和 Setter 
 * - 通过 `@Getter` 和 `@Setter` 注解，Lombok 自动生成了这两个方法。 
 * ### 总结 
 * - **用途**：这个类主要用于接收前端传递过来的课程编号列表，以便进行课程转储操作。 
 * - **设计模式**：它遵循了数据传输对象（DTO）的设计模式，用于在不同层之间传递数据。 
 * - **序列化支持**：由于实现了 `Serializable` 接口，该类的对象可以在网络上传输或保存到文件系统中。 
 * 这段代码简洁明了，使用了 Lombok 来减少样板代码，并且通过 Swagger 注解来提供文档化的信息。 
**/
@Getter
@Setter
@ApiModel
public class CourseDumpReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("课程编号")
	private List<String> resourceIds;

}

