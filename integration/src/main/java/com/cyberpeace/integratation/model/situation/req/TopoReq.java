package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopoReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("拓扑ID")
	private String topoId;

	@ApiModelProperty("平台ID")
	private String platformId;

	@ApiModelProperty("层级")
	private Integer level;

}
