/**
 * 该代码定义了一个名为 `LabelReq` 的 Java 类，位于 `com.cyberpeace.cr.crbase.controller.req` 包中。以下是代码的主要分析和总结： 
 * ### 功能描述 
 * - 该类是一个用于接收标签请求数据的实体类。 
 * - 它主要用于封装前端传递的标签信息，并通过注解进行校验和文档生成。 
 * ### 关键点分析 
 * 1. **注解说明**： 
 * - 使用了 `@Getter` 和 `@Setter` 注解（来自 Lombok），自动生成字段的 getter 和 setter 方法。 
 * - `@ApiModelProperty` 注解（来自 Swagger）用于标注字段在 API 文档中的描述，这里表示标签是“雷达图维度”。 
 * - `@NotNull` 注解（来自 JSR 380）表示该字段不能为空。 
 * 2. **字段说明**： 
 * - `labels` 是一个 `List<String>` 类型的字段，用于存储标签列表。 
 * - 被标记为必填项（`@NotNull`），确保前端必须传递非空的标签列表。 
 * 3. **依赖库**： 
 * - 使用了 Lombok 库简化代码。 
 * - 使用了 Swagger 注解，方便生成 API 文档。 
 * ### 总结 
 * 该类是一个简单的数据传输对象（DTO），用于接收标签信息，并结合注解实现数据校验和 API 文档生成。代码结构清晰，功能明确，适合用于后端接收和验证标签请求数据。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 标签请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class LabelReq {

	/**
	 * 标签
	 */
	@ApiModelProperty(value = "雷达图维度")
 	@NotNull
	private List<String> labels;
 

}
