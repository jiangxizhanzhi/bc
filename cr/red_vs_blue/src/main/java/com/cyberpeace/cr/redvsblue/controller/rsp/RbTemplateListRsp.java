package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import lombok.Data;

@Data
public class RbTemplateListRsp {

	private String resourceId;

	private BaseInfoRsp crEventInfo;

	private Integer nodesCount;

	private List<RbTemplateRoleRsp> roleInfo;

}
