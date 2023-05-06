package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点快照删除请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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
