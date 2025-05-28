/**
 * 这段代码定义了一个名为 `EvalIndexRefReq` 的 Java 类，该类用于表示评估指数裁判请求体。以下是对该代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.cr.evaluation.controller.req; 
 * ``` 
 * - 这行代码声明了类所在的包路径。 
 * ### 导入语句 
 * ```java 
 * import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import javax.validation.constraints.NotEmpty; 
 * ``` 
 * - 导入了必要的类和注解： 
 * - `EvalRecordSpecialistContext`：用于表示专家评分配置。 
 * - `ApiModelProperty`：用于生成 API 文档。 
 * - `Getter` 和 `Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `NotEmpty`：用于验证字段不能为空。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class EvalIndexRefReq { 
 * ``` 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 类名为 `EvalIndexRefReq`，表示评估指数裁判请求体。 
 * ### 字段声明 
 * ```java 
 * /** 
 * * 资源id 
 * *
 * @ApiModelProperty(value = "评估体系Id", required = true)
 * @NotEmpty 
 * private String resourceId; 
 * ``` 
 * - `resourceId`：资源 ID，必须提供且不能为空。 
 * ```java 
 * /** 
 * * 水平 
 * *
 * @ApiModelProperty("等级") 
 * private Integer level; 
 * ``` 
 * - `level`：水平或等级，表示某种标准。 
 * ```java 
 * /** 
 * * 专家 
 * *
 * @ApiModelProperty("指标项专家评分配置") 
 * private EvalRecordSpecialistContext specialist; 
 * ``` 
 * - `specialist`：专家评分配置对象。 
 * ```java 
 * /** 
 * * 类型 
 * *
 * @ApiModelProperty("评分方式 1系统 2 专家") 
 * private Integer type; 
 * ``` 
 * - `type`：评分方式，可以是系统评分（1）或专家评分（2）。 
 * ```java 
 * /** 
 * * 装置id 
 * *
 * @ApiModelProperty("系统评分时 需要节点id") 
 * private String deviceId; 
 * ``` 
 * - `deviceId`：系统评分时需要的节点 ID。 
 * ```java 
 * /** 
 * * 探针id 
 * *
 * @ApiModelProperty("系统评分时 需要探针id") 
 * private String probeId; 
 * ``` 
 * - `probeId`：系统评分时需要的探针 ID。 
 * ### 总结 
 * - `EvalIndexRefReq` 类主要用于表示评估指数裁判请求体，包含了资源 ID、水平、专家评分配置、评分方式、装置 ID 和探针 ID 等信息。 
 * - 使用了 Lombok 的注解来简化代码，并通过 Swagger 注解来生成 API 文档。 
 * - 字段中的 `resourceId` 是必需的，并且不允许为空。 
**/
package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 评估指数裁判请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalIndexRefReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotEmpty
	private String resourceId;

	/**
	 * 水平
	 */
	@ApiModelProperty("等级")
	private Integer level;

	/**
	 * 专家
	 */
	@ApiModelProperty("指标项专家评分配置")
	private EvalRecordSpecialistContext specialist;

	/**
	 * 类型
	 */
	@ApiModelProperty("评分方式 1系统 2 专家")
	private Integer type;

	/**
	 * 装置id
	 */
	@ApiModelProperty("系统评分时 需要节点id")
	private String deviceId;

	/**
	 * 探针id
	 */
	@ApiModelProperty("系统评分时 需要探针id")
	private String probeId;



}