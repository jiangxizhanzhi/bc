package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestSceneReq implements Serializable {

	@ApiModelProperty(value = "业务模板id", required = true)
	private String businessTemplateId;
}
