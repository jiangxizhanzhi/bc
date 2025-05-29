package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;
import lombok.Setter;

/**
 * Eval Task Slim 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskSlimContext` 的类，该类位于包 `com.cyberpeace.cr.evaluation.service.context` 下。以下是对其主要特性和功能的分析： 
 * ### 类定义与注释 
 * - **类名**：`EvalTaskSlimContext` 
 * - **作者**：SN 
 * - **日期**：2024年11月8日 
 * - **描述**：这是一个用于存储评估任务上下文信息的类。 
 * ### 类特性 
 * - **Lombok 注解**： 
 * - `@Getter`：为类中的所有字段生成 getter 方法。 
 * - `@Setter`：为类中的所有字段生成 setter 方法。 
 * ### 字段（成员变量） 
 * - **taskId**：任务ID，类型为 `String`。 
 * - **taskName**：任务名称，默认值根据语言环境设置（中文或英文）。 
 * - **mpSceneId**：中台场景ID，类型为 `String`。 
 * ### 方法 
 * - **setResourceId(String resourceId)**：设置资源ID，并将其赋值给 `taskId`。 
 * ### 详细说明 
 * - **国际化支持**：`taskName` 字段根据语言环境（由 `CommonContext.getLanguage()` 返回）的不同，显示不同的默认值。 
 * - **资源ID映射**：`setResourceId` 方法将传入的 `resourceId` 设置为 `taskId`，这可能用于在不同上下文中使用相同的标识符。 
 * ### 总结 
 * 这个类主要用于封装评估任务的基本信息，如任务ID、任务名称和中台场景ID。通过Lombok注解简化了代码，使其更简洁。同时，它还提供了一种方式来处理国际化，使应用可以适应不同的语言环境。 
**/
@Getter
@Setter
public class EvalTaskSlimContext {

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 任务名称
	 */
	private String taskName = CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN) ? "Current test instance" : "当前测试实例";

	/**
	 * 中台场景id
	 */
	private String mpSceneId;

	public void setResourceId(String resourceId) {
		this.taskId = resourceId;
	}

}

