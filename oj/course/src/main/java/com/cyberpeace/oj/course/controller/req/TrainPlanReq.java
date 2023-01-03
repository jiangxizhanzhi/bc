package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanReq extends PageReqBase {

	@ApiModelProperty(name = "organization_id", value = "组织id")
	private String organizationId;

	public void setOrganization_id(String organizationId) {
		this.organizationId = organizationId;
	}
}
