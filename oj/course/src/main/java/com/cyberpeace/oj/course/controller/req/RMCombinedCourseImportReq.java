package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.course.service.bo.CombinedCourseImportContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * rm组合课程导入请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `RMCombinedCourseImportReq` 的 Java 类，该类用于表示组合课程导入请求的数据模型。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`RMCombinedCourseImportReq` 
 * - **实现接口**：`Serializable`，表示该类的对象可以被序列化。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter`：为类的所有字段生成 getter 方法。 
 * - `@Setter`：为类的所有字段生成 setter 方法。 
 * - `@ApiModel`：表示这是一个 Swagger API 模型，用于文档生成。 
 * - **Swagger 注解**： 
 * - `@ApiModelProperty`：用于描述类或属性在 Swagger 文档中的说明。 
 * ### 字段 
 * - **courseInfos**（类型：`List<CombinedCourseImportContext>`） 
 * - 注解： 
 * - `@ApiModelProperty`：描述该字段在 Swagger 文档中的说明。 
 * - `@NotNull`：确保该字段不为空。 
 * ### 用途 
 * - 该类主要用于接收组合课程导入请求的数据。 
 * - 具体来说，它包含一个 `List<CombinedCourseImportContext>` 类型的字段 `courseInfos`，用于存储多个课程导入上下文信息。 
 * - 通过 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成了相应的 getter 和 setter 方法，方便外部访问和修改字段值。 
 * - 使用 `@NotNull` 确保传入的课程信息列表不能为空。 
 * ### 总结 
 * 这个类是一个数据传输对象（DTO），用于封装组合课程导入请求的数据。它实现了序列化接口，并使用 Lombok 和 Swagger 注解来简化代码和生成相关文档。 
**/
@Getter
@Setter
@ApiModel
public class RMCombinedCourseImportReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 课程信息
	 *
	 * @see List<CombinedCourseImportContext>
	 */
	@ApiModelProperty("组合课程导入参数")
	@NotNull
	private List<CombinedCourseImportContext> courseInfos;
}

