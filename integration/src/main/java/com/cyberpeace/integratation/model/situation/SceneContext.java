package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneContext implements Serializable {

	@ApiModelProperty("拓扑ID")
	private String id;

	@ApiModelProperty("拓扑名称")
	private String name;

	@ApiModelProperty("拓扑描述")
	private String desc;

	@ApiModelProperty("总层级")
	private Float hierarchy;

}
