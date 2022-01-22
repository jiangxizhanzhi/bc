package com.cyberpeace.cr.singletrain.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import lombok.Data;

@Data
public class SingleTrainTemplateReq extends BasicTemplate {

	private CrRoleConfigMissionsScheme missionsScheme;

	private String resourceId;

}
