package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomPageReq` 的类，该类继承自 `PageReqBase`。这个类主要用于处理课堂分页请求的相关信息。以下是对该代码的简要分析： 
 * ### 类和属性 
 * - **类名**: `ClassroomPageReq` 
 * - **继承**: `PageReqBase` 
 * - **注解**: 使用了 `@Getter` 和 `@Setter` 注解，自动为类中的属性生成 getter 和 setter 方法。 
 * - **Swagger 注解**: 使用了 `@ApiModelProperty` 注解来描述类的属性。 
 * ### 属性 
 * - **trainPlanId** (String): 训练计划的ID，默认值为空字符串。 
 * - **organizationId** (String): 组织的ID。 
 * ### 方法 
 * - **setTrain_plan_id(String trainPlanId)**: 设置训练计划ID的方法。如果传入的 `trainPlanId` 等于 `TrainPlan.TASK_SET_ID`，则将其设置为空字符串。 
 * - **setOrganization_id(String organizationId)**: 设置组织ID的方法。 
 * ### 总结 
 * - **功能**: 该类用于封装课堂分页请求的数据，包括训练计划ID和组织ID。 
 * - **特点**: 通过继承 `PageReqBase` 类，可能包含了分页相关的属性和方法。使用 Lombok 注解简化了代码，减少了样板代码。 
 * - **注意事项**: 在设置 `trainPlanId` 时，有一个特殊处理逻辑，即当 `trainPlanId` 等于 `TrainPlan.TASK_SET_ID` 时，将其设置为空字符串。 
 * ### 优点 
 * - 使用 Lombok 减少了样板代码。 
 * - 使用 Swagger 注解便于 API 文档生成。 
 * ### 改进建议 
 * - 可以考虑将 `setTrain_plan_id` 方法中的特殊处理逻辑提取到一个单独的方法中，以提高代码的可读性和复用性。 
 * - 如果 `TrainPlan.TASK_SET_ID` 是一个常量，可以将其定义为静态常量，以提高代码的可维护性。 
**/
@Getter
@Setter

public class ClassroomPageReq extends PageReqBase {

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "train_plan_id", value = "训练计划编号")
	private String trainPlanId = "";

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "organization_id", value = "组织id")
	private String organizationId;

	public void setTrain_plan_id(String trainPlanId) {
		//约定临时计划的id需要转换
		if (TrainPlan.TASK_SET_ID.equals(trainPlanId)) {
			trainPlanId = "";
		}
		this.trainPlanId = trainPlanId;
	}

	public void setOrganization_id(String organizationId) {
		this.organizationId = organizationId;
	}

}

