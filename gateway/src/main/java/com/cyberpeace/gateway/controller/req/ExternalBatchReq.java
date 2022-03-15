package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ExternalBatchReq implements Serializable {

	@ApiModelProperty("资源编号数组")
	@NotEmpty
	private List<String> resourceIds;

	public void setResource_ids(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
}
