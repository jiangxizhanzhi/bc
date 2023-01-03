package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class ClassroomPageReq extends PageReqBase {

	@ApiModelProperty(name = "train_plan_id", value ="训练计划编号")
	private String trainPlanId;

	public void setTrain_plan_id(String trainPlanId) {
		this.trainPlanId = trainPlanId;
	}

}
