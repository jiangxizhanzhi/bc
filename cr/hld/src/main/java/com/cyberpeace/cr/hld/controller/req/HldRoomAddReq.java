package com.cyberpeace.cr.hld.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.hld.service.context.HldRoleConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class HldRoomAddReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String taskId;

	@ApiModelProperty(required = false)
	private List<HldRoleConfig> whiteRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<HldRoleConfig> redRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<HldRoleConfig> blueRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

}