package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练计划请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TrainPlanReq extends PageReqBase {

	/**
	 * 组织id
	 */
	@ApiModelProperty(name = "organization_id", value = "组织id")
	private String organizationId;

	public void setOrganization_id(String organizationId) {
		this.organizationId = organizationId;
	}
}
