package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class NodeSnapShotReq implements Serializable {

	@ApiModelProperty("场景编号")
	@NotBlank
	private String sceneId;

	@ApiModelProperty("节点编号")
	@NotBlank
	private String nodeId;

	@ApiModelProperty("快照名称")
	@NotBlank
	private String snapshotName;

	@ApiModelProperty("快照描述")
	private String description;
}
