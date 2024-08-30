package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanReq extends PageReqBase {

	@ApiModelProperty(name = "organization_id", value = "组织id")
	private String organizationId;

	@ApiModelProperty("状态, 1=未开始，2=进行中，3=已结束")
	private Integer status;

	public void setOrganization_id(String organizationId) {
		this.organizationId = organizationId;
	}
}
