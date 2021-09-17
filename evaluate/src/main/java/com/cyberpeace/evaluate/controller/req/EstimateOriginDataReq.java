package com.cyberpeace.evaluate.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class EstimateOriginDataReq extends PageReqBase {

	@NotBlank
	@ApiModelProperty("评估实例id")
	private String instance_id;

	@NotBlank
	@ApiModelProperty("变量项id")
	private String variable_id;

	public String getInstanceId() {
		return instance_id;
	}

	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}

	public String getVariableId() {
		return variable_id;
	}

	public void setVariable_id(String variable_id) {
		this.variable_id = variable_id;
	}
}
