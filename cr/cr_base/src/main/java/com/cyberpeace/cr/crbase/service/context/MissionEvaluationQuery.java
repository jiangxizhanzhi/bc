package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务评估查询体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `MissionEvaluationQuery` 的 Java 类，用于表示任务评估查询的请求体。以下是简要分析： 
 * 1. **继承关系**： 
 * - 该类继承自 `PageReqBase`，表明它可能包含分页相关的属性或逻辑。 
 * 2. **注解与功能**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档生成提供字段描述。 
 * 3. **字段说明**： 
 * - `roomId`：表示房间 ID，用于标识特定的房间。 
 * - `studentHash`：表示学员的唯一标识（可能是散列值）。 
 * - `evaluateType`：表示评估的方式或类型。 
 * - `evaluateStatus`：表示评估的状态，使用整数类型表示。 
 * 4. **用途**： 
 * - 该类通常作为参数传递给后端接口，用于查询任务评估的相关数据。 
 * 总结：这是一个典型的请求体类，用于封装任务评估查询所需的参数，并支持 Swagger 文档生成和 Lombok 的便捷开发特性。 
**/
@Getter
@Setter
public class MissionEvaluationQuery extends PageReqBase {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	/**
	 * 学员散 列
	 */
	@ApiModelProperty(value = "学员ID")
	private String studentHash;

	/**
	 * 评估类型
	 */
	@ApiModelProperty(value = "评估方式")
	private String evaluateType;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

}

