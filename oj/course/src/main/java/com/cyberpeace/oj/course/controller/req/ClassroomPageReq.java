package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂分页请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data

public class ClassroomPageReq extends PageReqBase {

	/**
	 * 训练计划id
	 */
	@ApiModelProperty(name = "train_plan_id", value = "训练计划编号")
	private String trainPlanId = "";

	/**
	 * 组织id
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
