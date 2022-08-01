package com.cyberpeace.cs.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class NodeSnapshotDeleteReq implements Serializable {

	@ApiModelProperty("场景编号")
	@NotBlank
	private String sceneId;

	@ApiModelProperty("节点编号")
	@NotEmpty
	private String nodeId;

	@ApiModelProperty("快照编号")
	@NotBlank
	private String snapshotId;

}
