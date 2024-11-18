package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 节点折拍摄请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class NodeSnapShotReq implements Serializable {

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
	@NotBlank
	private String nodeId;

	/**
	 * 快照名称
	 *
	 * @see String
	 */
	@ApiModelProperty("快照名称")
	@NotBlank
	private String snapshotName;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("快照描述")
	private String description;
}
