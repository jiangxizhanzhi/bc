package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 合并快照要求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@ApiModel
public class MergeSnapshotReq implements Serializable {

	@ApiModelProperty(value = "镜像类型", required = true)
	@NotBlank
	private String imageType;

	@ApiModelProperty(value = "合并后的名称")
	private String newName;

	@ApiModelProperty(value = "最后一个快照的名称", required = true)
	@NotBlank
	private String snapshotName;

	@ApiModelProperty(value = "镜像id", required = true)
	@NotBlank
	private String uuid;

	@ApiModelProperty(value = "场景编号", required = true)
	private String sceneId;
}
