package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRoleRsp;

import lombok.Data;

@Data
public class RbTemplateListRsp {

	private String resourceId;

	private BaseInfoRsp crEventInfo;

	private Integer nodesCount;

	private List<RbTemplateRoleRsp> roleInfo;

}
