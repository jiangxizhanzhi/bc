package com.cyberpeace.cr.crbase.controller.rsp.constants;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionCategoryRsp implements Serializable {

	@ApiModelProperty("类型id")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	public MissionCategoryRsp(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
