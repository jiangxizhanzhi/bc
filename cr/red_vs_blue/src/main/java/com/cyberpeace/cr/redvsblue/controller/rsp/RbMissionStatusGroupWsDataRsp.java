package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
public class RbMissionStatusGroupWsDataRsp extends RbMissionStatusGroupDataRsp {


	@ApiModelProperty(value = "角色")
	private Integer group;

	@ApiModelProperty(value = "IP")
	private String destIp;

	@ApiModelProperty(value = "节点名称")
	private String destNode;
}
