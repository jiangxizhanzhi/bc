/**
 * 这段代码定义了一个名为 `MissionEvaluationListReq` 的 Java 类，用于表示任务评估列表请求的数据结构。以下是代码的简要分析： 
 * 1. **类的继承与注解**： 
 * - 该类继承自 `PageReqBase`，表明它可能包含分页相关的属性。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了所有字段的 getter 和 setter 方法。 
 * - 使用了 `@ApiModel` 注解，表明这是一个 API 模型类，通常用于 Swagger 文档生成。 
 * 2. **字段及注解**： 
 * - `roomId`：房间 ID，使用了 `@NotNull` 和 `@ApiModelProperty` 注解，表示这是一个必填字段且会在 Swagger 文档中显示其描述。 
 * - `studentHash`：学员散列值，用于标识学员。 
 * - `evaluateType`：评估类型，表示评估的方式。 
 * - `isShowEvaluated`：是否显示已评估的记录，也是必填字段，表示是否只展示未评估的记录。 
 * 3. **用途**： 
 * - 这个类主要用于接收前端传来的任务评估列表请求参数，支持分页、过滤等功能。 
 * 总体来说，这个类是一个典型的请求对象类，用于封装前端请求参数，并通过注解增强了其可读性和文档化能力。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务评估列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
@Getter
@Setter
public class MissionEvaluationListReq extends PageReqBase {

	/**
	 * 房间id
	 */
	@NotNull
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
	private Integer evaluateType;

	/**
	 * 显示已评估
	 */
	@NotNull
	@ApiModelProperty(value = "只展示未评估")
	private Boolean isShowEvaluated;

}
