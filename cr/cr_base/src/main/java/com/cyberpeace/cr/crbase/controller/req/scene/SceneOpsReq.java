package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneOpsReq implements Serializable {

	@ApiModelProperty(value = "业务实例id", required = true)
	private String roomId;
}
