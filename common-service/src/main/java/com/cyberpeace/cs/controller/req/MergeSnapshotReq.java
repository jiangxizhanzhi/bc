package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 合并快照请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class MergeSnapshotReq implements Serializable {

	/**
	 * 图像类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "镜像类型", required = true)
	@NotBlank
	private String imageType;

	/**
	 * 新增功能名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "合并后的名称")
	private String newName;

	/**
	 * 快照名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "最后一个快照的名称", required = true)
	@NotBlank
	private String snapshotName;

	/**
	 * uuid
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "镜像id", required = true)
	@NotBlank
	private String uuid;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景编号", required = true)
	private String sceneId;
}
