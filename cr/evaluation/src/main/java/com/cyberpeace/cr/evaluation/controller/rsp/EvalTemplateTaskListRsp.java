package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估模板任务列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTemplateTaskListRsp` 的 Java 类，该类用于表示评估模板任务列表的响应。以下是对该代码的简要分析： 
 * ### 类名及包声明 
 * - **包声明**：`package com.cyberpeace.cr.evaluation.controller.rsp;` 
 * - 表示这个类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * - **类名**：`EvalTemplateTaskListRsp` 
 * - 这个类是用于表示评估模板任务列表响应的。 
 * ### 导入的类 
 * - `java.io.Serializable` 
 * - `EvalTemplateTaskListRsp` 类实现了 `Serializable` 接口，这意味着它可以被序列化，便于在网络上传输或持久化存储。 
 * - `java.util.List` 
 * - 用于表示一个任务列表。 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext` 
 * - `EvalTaskSlimContext` 是一个上下文对象，用于封装评估任务的简要信息。 
 * - `io.swagger.annotations.ApiModelProperty` 
 * - 用于为类中的字段添加元数据注解，以便在生成的 API 文档中显示这些字段的信息。 
 * ### 类注释 
 * - 类注释描述了该类的作用：“评估模板任务列表响应”。 
 * ### 字段及注释 
 * - `private List<EvalTaskSlimContext> taskList;` 
 * - 用于存储当前的评估任务列表。 
 * - 注释说明这是“当前测评任务列表”。 
 * - `private String currentTaskId;` 
 * - 用于存储当前的评估任务 ID。 
 * - 注释说明这是“当前测评任务”。 
 * ### Getter 和 Setter 方法 
 * - 使用 `@Getter` 和 `@Setter` 注解自动生成了 `taskList` 和 `currentTaskId` 字段的 getter 和 setter 方法。 
 * ### 总结 
 * - `EvalTemplateTaskListRsp` 类是一个数据传输对象（DTO），用于封装评估模板任务列表的响应信息。 
 * - 它包含两个字段：`taskList` 用于存储任务列表，`currentTaskId` 用于存储当前任务的 ID。 
 * - 通过 Swagger 注解，可以为这些字段生成详细的 API 文档。 
 * - 类实现了 `Serializable` 接口，方便在网络上传输或持久化存储。 
 * 这个类的设计简洁明了，符合 RESTful API 设计的最佳实践。 
**/
@Getter
@Setter
public class EvalTemplateTaskListRsp implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 任务列表
	 */
	@ApiModelProperty(value = "当前测评任务列表")
	private List<EvalTaskSlimContext> taskList;

	/**
	 * 当前任务id
	 */
	@ApiModelProperty(value = "当前测评任务")
	private String currentTaskId;

}

