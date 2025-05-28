/**
 * 这段代码定义了一个名为 `EvalTimeLineContext` 的 Java 类，该类用于表示某个评估（evaluation）的时间线上下文。以下是对其主要特性的简要分析： 
 * ### 包声明和导入 
 * - 代码位于 `com.cyberpeace.cr.evaluation.service.context` 包中。 
 * - 导入了 `java.util.Date` 类和 `io.swagger.annotations.ApiModelProperty` 注解，以及 `lombok.Getter` 和 `lombok.Setter` 注解。 
 * ### 类注释 
 * - 类注释说明这是一个用于评估的时间线上下文，并标注了作者和日期。 
 * ### 类特性 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法，从而减少了样板代码。 
 * - 类中的三个属性： 
 * - `stepId`：表示步骤ID，类型为 `String`。 
 * - `title`：表示步骤标题，类型为 `String`。 
 * - `endTime`：表示结束时间，类型为 `Date`。 
 * - 这些属性都使用了 Swagger 的 `@ApiModelProperty` 注解来描述它们的用途，方便生成 API 文档。 
 * ### 构造函数 
 * - 提供了两个构造函数： 
 * - 一个无参构造函数，用于在不需要初始化特定属性时创建对象。 
 * - 一个带参数的构造函数，接收 `title` 和 `endTime` 参数，并将它们分别赋值给相应的属性。 
 * ### 自定义方法 
 * - `setResourceId` 方法：将传入的 `resourceId` 赋值给 `stepId` 属性。这个方法的作用是将资源ID转换为步骤ID，可能是因为在某些场景下，资源ID和步骤ID是等价的。 
 * ### 总结 
 * `EvalTimeLineContext` 类是一个简单的数据传输对象（DTO），用于封装评估步骤的相关信息。它通过构造函数提供了一定程度的初始化灵活性，并且通过自定义的方法 `setResourceId` 提供了额外的功能。Lombok 和 Swagger 注解的使用简化了代码并增强了可读性。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Eval 时间线上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTimeLineContext {

	/**
	 * 步id
	 */
	@ApiModelProperty("步骤Id")
	private String stepId;

	/**
	 * 标题
	 */
	@ApiModelProperty("步骤标题")
	private String title;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("完成时间")
	private Date endTime;

	public void setResourceId(String resourceId) {
		this.stepId = resourceId;
	}

	public EvalTimeLineContext(String title, Date endTime) {
		this.title = title;
		this.endTime = endTime;
	}

	public EvalTimeLineContext() {}

}
