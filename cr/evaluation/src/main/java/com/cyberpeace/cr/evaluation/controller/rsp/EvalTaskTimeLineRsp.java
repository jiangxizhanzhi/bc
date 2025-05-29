package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务时间线响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskTimeLineRsp` 的 Java 类，用于表示评估任务的时间线响应。以下是对该类的简要分析： 
 * ### 类名与包名 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - 这个包名表明该类属于一个响应对象（response），具体是评估任务时间线相关的响应。 
 * - **类名**: `EvalTaskTimeLineRsp` 
 * - 类名明确表示这是一个评估任务时间线响应对象。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的 getter 和 setter 方法的生成。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述 API 文档中的属性。 
 * ### 属性 
 * - `taskId`: 任务ID，用于唯一标识一个评估任务。 
 * - `startTime`: 任务的开始时间。 
 * - `endTime`: 任务的结束时间。 
 * - `data`: 包含一系列 `EvalTimeLineContext` 对象的列表，这些对象记录了评估任务的各个步骤。 
 * - `currentTime`: 当前时间，可能用于显示或比较。 
 * ### 总结 
 * - 这是一个简单的数据传输对象（DTO），用于在控制器层返回评估任务的时间线信息。 
 * - 类的设计清晰明了，使用了注解来简化代码，并且提供了必要的属性来表示评估任务的时间线信息。 
 * - 通过 Lombok 的注解，减少了样板代码，使得类更加简洁。 
 * - 使用 Swagger 注解使得这个类更容易被 API 文档工具识别，方便生成文档。 
 * 总体来说，这是一个功能明确、设计良好的类，适用于在 API 响应中传递评估任务的时间线信息。 
**/
@Getter
@Setter
public class EvalTaskTimeLineRsp implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private Date endTime;

	/**
	 * 数据
	 */
	@ApiModelProperty("测评步骤记录")
	private List<EvalTimeLineContext> data;

	/**
	 * 当前时间
	 */
	@ApiModelProperty("当前时间")
	private Date currentTime;

}

