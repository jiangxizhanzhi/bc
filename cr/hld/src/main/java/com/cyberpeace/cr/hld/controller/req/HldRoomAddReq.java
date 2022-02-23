package com.cyberpeace.cr.hld.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.hld.service.context.HldRoleConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HldRoomAddReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String taskId;

	@ApiModelProperty(required = true)
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