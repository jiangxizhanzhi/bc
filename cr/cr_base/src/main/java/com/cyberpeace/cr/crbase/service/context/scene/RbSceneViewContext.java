package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbSceneViewContext extends SceneViewContext implements Serializable {

	@ApiModelProperty(value = "红方角色")
	private List<CrRoleConfig> redRoles;

	@ApiModelProperty(value = "蓝方角色")
	private List<CrRoleConfig> blueRoles;
}
