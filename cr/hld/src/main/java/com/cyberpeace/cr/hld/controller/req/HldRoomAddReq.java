package com.cyberpeace.cr.hld.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class HldRoomAddReq {

	@ApiModelProperty(required = false)
	private List<CrRoleConfig> whiteRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<CrRoleConfig> redRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<CrRoleConfig> blueRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

}