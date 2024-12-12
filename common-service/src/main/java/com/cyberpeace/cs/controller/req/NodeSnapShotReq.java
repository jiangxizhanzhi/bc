package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 节点折拍摄请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class NodeSnapShotReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景编号")
	@NotBlank
	private String sceneId;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点编号")
	@NotBlank
	private String nodeId;

	/**
	 * 快照名称
	 */
	@ApiModelProperty("快照名称")
	@NotBlank
	private String snapshotName;

	/**
	 * 描述
	 */
	@ApiModelProperty("快照描述")
	private String description;
}
