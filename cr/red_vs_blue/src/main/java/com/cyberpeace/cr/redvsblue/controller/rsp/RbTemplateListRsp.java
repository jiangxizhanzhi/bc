package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateListRsp {

	@ApiModelProperty(value = "模板id")
	private String resourceId;

	@ApiModelProperty(value = "基本信息")
	private BaseInfoRsp crEventInfo;

	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	@ApiModelProperty(value = "角色信息")
	private List<RbTemplateRoleRsp> roleInfo;

}
