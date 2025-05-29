package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 新增课实践请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AddLessonPracticeReq` 的 Java 类，用于表示添加课程实践请求的参数。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包声明**：该类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * - **导入的类**： 
 * - `Serializable`：表示该类可以序列化。 
 * - `PaperPracticeContext`：表示习题上下文对象。 
 * - `ApiModel` 和 `ApiModelProperty`：来自 Swagger 注解库，用于生成 API 文档。 
 * - `Getter` 和 `Setter`：来自 Lombok 库，用于自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - 作者：SN 
 * - 版本：1.0.0 
 * - 创建日期：2024年11月8日 
 * ### 类属性 
 * - **resourceId** 
 * - **类型**：`String` 
 * - **注释**：课程编号 
 * - **约束**：必须非空（使用了 `@NotBlank` 注解） 
 * - **practiceList** 
 * - **类型**：`List<PaperPracticeContext>` 
 * - **注释**：习题数据 
 * ### 使用的技术和库 
 * - **Lombok**：简化了代码，通过 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * - **Swagger**：通过 `@ApiModel` 和 `@ApiModelProperty` 注解来描述 API 模型和字段，便于生成 API 文档。 
 * ### 总结 
 * 该类是一个用于处理添加课程实践请求的数据传输对象 (DTO)。它包含两个主要属性：课程编号 (`resourceId`) 和习题数据列表 (`practiceList`)。这些属性都通过 Swagger 注解进行了文档化，并且 `resourceId` 属性还被标记为必填项。通过 Lombok 库，该类自动实现了 getter 和 setter 方法，从而减少了样板代码。 
**/
@Getter
@Setter
@ApiModel
public class AddLessonPracticeReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String resourceId;

	/**
	 * 执业名单
	 *
	 * @see List<PaperPracticeContext>
	 */
	@ApiModelProperty("习题数据")
	private List<PaperPracticeContext> practiceList;

}

