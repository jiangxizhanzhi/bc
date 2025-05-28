/**
 * 这段代码定义了一个名为 `EvaTemplateAddReq` 的 Java 类，该类用于处理添加伊娃模板（Eva Template）时的请求体。以下是对其主要特性和功能的简要分析： 
 * 1. **包声明**：该类位于 `com.cyberpeace.cr.evaluation.controller.req` 包中，表明它是一个与评估控制器相关的请求对象。 
 * 2. **导入的类**： 
 * - `java.util.List`: 用于表示场景配置数据的列表。 
 * - `javax.validation.constraints.NotNull`: 注解，用于确保某些字段不为空。 
 * - 其他导入的类如 `SceneConfigData` 和 `EvalTaskStepConfig` 分别代表场景配置数据和评估任务步骤配置。 
 * 3. **继承**：`EvaTemplateAddReq` 继承自 `EvaTemplateBaseInfoReq` 类。这可能意味着 `EvaTemplateAddReq` 类包含了所有基类中的信息，并在此基础上增加了新的属性。 
 * 4. **注解**： 
 * - `@Getter` 和 `@Setter`：Lombok 提供的注解，用于自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：Swagger 注解，用于生成 API 文档。这些注解提供了关于字段的描述和是否必填的信息。 
 * 5. **字段**： 
 * - `sceneConfigData`: 场景配置数据，使用 `@NotNull` 注解确保其非空。 
 * - `stepConfig`: 一个 `EvalTaskStepConfig` 对象的列表，同样使用 `@NotNull` 注解确保其非空。 
 * 6. **文档**：类和字段都有相应的 Javadoc 注释，提供了一些关于类用途和字段含义的基本信息。 
 * 总结来说，`EvaTemplateAddReq` 是一个用于处理添加伊娃模板请求的 Java 类，包含场景配置数据和步骤配置两个重要属性，并且使用了 Lombok 和 Swagger 注解来简化开发过程并生成文档。 
**/
package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 伊娃模板新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvaTemplateAddReq extends EvaTemplateBaseInfoReq {

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	/**
	 * 步骤配置
	 */
	@ApiModelProperty(value = "步骤配置", required = true)
	@NotNull
	private List<EvalTaskStepConfig> stepConfig;

}