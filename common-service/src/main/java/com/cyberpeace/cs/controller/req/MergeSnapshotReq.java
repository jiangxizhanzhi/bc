package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 合并快照请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class MergeSnapshotReq implements Serializable {

	/**
	 * 图像类型
	 */
	@ApiModelProperty(value = "镜像类型", required = true)
	@NotBlank
	private String imageType;

	/**
	 * 新增功能名称
	 */
	@ApiModelProperty(value = "合并后的名称")
	private String newName;

	/**
	 * 快照名称
	 */
	@ApiModelProperty(value = "最后一个快照的名称", required = true)
	@NotBlank
	private String snapshotName;

	/**
	 * uuid
	 */
	@ApiModelProperty(value = "镜像id", required = true)
	@NotBlank
	private String uuid;

	/**
	 * 场景id
	 */
	@ApiModelProperty(value = "场景编号", required = true)
	private String sceneId;
}
