package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionSchemeTemplateModifyReq extends MissionSchemeTemplateReq implements Serializable {

	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;
}
