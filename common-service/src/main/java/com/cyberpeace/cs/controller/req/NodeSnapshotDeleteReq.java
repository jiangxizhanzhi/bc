package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点快照删除请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class NodeSnapshotDeleteReq implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景编号")
	@NotBlank
	private String sceneId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点编号")
	@NotEmpty
	private String nodeId;

	/**
	 * 快照id
	 *
	 * @see String
	 */
	@ApiModelProperty("快照编号")
	@NotBlank
	private String snapshotId;

}
