package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.util.TimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomListReq` 的 Java 类，用于表示课堂列表请求体。以下是该类的详细分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - **导入语句**： 
 * - 导入了 `java.io.Serializable` 接口。 
 * - 导入了 `java.util.Date` 类。 
 * - 导入了 `javax.validation.constraints.NotNull` 注解。 
 * - 导入了 `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 注解。 
 * - 导入了 `com.cyberpeace.common.util.TimeUtil` 工具类。 
 * - 使用了 `lombok` 库中的 `Getter` 和 `Setter` 注解。 
 * ### 类注释 
 * - 作者：SN 
 * - 版本：1.0.0 
 * - 日期：2024年11月8日 
 * - 实现了 `Serializable` 接口 
 * ### 类属性 
 * - `startTime`：表示开始时间，使用 `@NotNull` 注解确保其不为空。 
 * - `endTime`：表示结束时间，使用 `@NotNull` 注解确保其不为空。 
 * - `organizationId`：表示组织ID。 
 * - `trainPlanId`：表示训练计划ID。 
 * ### Getter 和 Setter 方法 
 * - `setStart_time` 和 `getStartTime`：用于设置和获取开始时间，并将字符串格式的时间转换为 `Date` 对象。 
 * - `setEnd_time` 和 `getEndTime`：用于设置和获取结束时间，并将字符串格式的时间转换为 `Date` 对象。 
 * - `setOrganization_id` 和 `getOrganization_id`：用于设置和获取组织ID。 
 * - `setTrain_plan_id` 和 `getTrainPlanId`：用于设置和获取训练计划ID。 
 * ### 注解 
 * - `@ApiModel`：用于描述该类的模型信息。 
 * - `@ApiModelProperty`：用于描述类中各个属性的信息。 
 * ### 总结 
 * - `ClassroomListReq` 类主要用于封装课堂列表查询请求所需的数据，包括开始时间和结束时间等字段。 
 * - 通过 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成了 getter 和 setter 方法，简化了代码。 
 * - 使用 `@NotNull` 注解确保了某些字段（如 `startTime` 和 `endTime`）不能为空。 
 * - 通过 `TimeUtil.toDate2` 方法将字符串形式的时间转换为 `Date` 对象，方便后续处理。 
 * - 该类还包含了 Swagger 注解，用于生成 API 文档。 
**/
@ApiModel
@Getter
@Setter
public class ClassroomListReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 开始时间
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "start_time", value = "开始日期")
	@NotNull
	private String startTime;

	/**
	 * 结束时间
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "end_time", value = "结束日期")
	@NotNull
	private String endTime;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "organization_id", value = "组织编号")
	private String organizationId;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "train_plan_id", value = "训练计划编号")
	private String trainPlanId;

	public void setStart_time(String startTime) {
		this.startTime = startTime;
	}

	public void setEnd_time(String endTime) {
		this.endTime = endTime;
	}

	public void setOrganization_id(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getStartTime() {
		return TimeUtil.toDate2(startTime);
	}

	public Date getEndTime() {
		return TimeUtil.toDate2(endTime);
	}

	public String getTrainPlanId() {
		return trainPlanId;
	}

	public void setTrain_plan_id(String trainPlanId) {
		this.trainPlanId = trainPlanId;
	}
}

