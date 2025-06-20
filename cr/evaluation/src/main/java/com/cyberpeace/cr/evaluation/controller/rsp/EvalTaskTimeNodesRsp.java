package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务时间节点响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskTimeNodesRsp` 的 Java 类，用于表示评估任务的时间节点响应。以下是详细的分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.cr.evaluation.controller.rsp; 
 * import java.io.Serializable; 
 * import java.util.List; 
 * import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `package` 声明了该类所在的包。 
 * - 导入了必要的类：`Serializable`、`List`、`EvalTimeLineContext` 以及 Lombok 注解 `@Getter` 和 `@Setter`。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * public class EvalTaskTimeNodesRsp implements Serializable { 
 * ``` 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，意味着这个类的对象可以被序列化。 
 * ### 成员变量 
 * ```java 
 * /** 
 * * 任务id 
 * *
 * @ApiModelProperty("测评任务Id") 
 * private String taskId; 
 * /** 
 * * 数据 
 * *
 * @ApiModelProperty("测评步骤记录") 
 * private List<EvalTimeLineContext> data; 
 * ``` 
 * - `taskId`：任务 ID，使用了 Swagger 注解 `@ApiModelProperty` 描述其用途。 
 * - `data`：一个包含 `EvalTimeLineContext` 对象的列表，同样使用了 Swagger 注解 `@ApiModelProperty` 描述其用途。 
 * ### 总结 
 * - 这个类主要用于表示评估任务的时间节点响应。 
 * - 它有两个主要属性：任务 ID (`taskId`) 和评估步骤记录 (`data`)。 
 * - 使用了 Lombok 注解来简化代码，并且实现了 `Serializable` 接口以支持对象的序列化。 
 * - 通过 Swagger 注解描述了 API 文档中的字段信息。 
 * 这个类的设计简洁明了，适用于 RESTful API 的响应数据结构。 
**/
@Getter
@Setter
public class EvalTaskTimeNodesRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 任务id
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 数据
	 */
	@ApiModelProperty("测评步骤记录")
	private List<EvalTimeLineContext> data;

}

