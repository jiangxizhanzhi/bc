package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubmitScanInfoReq {

	@ApiModelProperty(value = "场景ID")
	private String sceneId ;

	@ApiModelProperty(value = "节点ID")
	private List<String> nodeIds;

}
