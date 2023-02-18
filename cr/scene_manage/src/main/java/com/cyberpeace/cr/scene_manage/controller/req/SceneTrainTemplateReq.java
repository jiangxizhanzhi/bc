package com.cyberpeace.cr.scene_manage.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import lombok.Data;

@Data
public class SceneTrainTemplateReq extends BasicTemplate {

	private CrRoleConfigMissionsScheme missionsScheme;

	private String resourceId;

}
