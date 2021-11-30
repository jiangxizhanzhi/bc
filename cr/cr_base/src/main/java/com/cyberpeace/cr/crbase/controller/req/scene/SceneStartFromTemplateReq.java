package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneStartFromTemplateReq implements Serializable {

	@ApiModelProperty(value = "场景模板id", required = true)
	private String templateId;
}
