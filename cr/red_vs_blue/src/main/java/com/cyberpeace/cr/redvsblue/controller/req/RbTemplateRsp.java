package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRoleRsp;

import lombok.Data;

@Data
public class RbTemplateRsp {

	private BaseInfoRsp baseInfo;

	private List<RbTemplateRoleRsp> redRoles;

	private List<RbTemplateRoleRsp> blueRoles;
}
