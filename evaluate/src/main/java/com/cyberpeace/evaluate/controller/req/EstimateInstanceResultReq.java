package com.cyberpeace.evaluate.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;

public class EstimateInstanceResultReq extends PageReqBase {

	@NotBlank
	@ApiModelProperty(name = "estimate_id", value = "评估id")
	private String estimate_id;

	public String getEstimateId() {
		return estimate_id;
	}

	public void setEstimate_id(String estimateId) {
		this.estimate_id = estimateId;
	}
}
