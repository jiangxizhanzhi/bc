package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点急射点播
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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
