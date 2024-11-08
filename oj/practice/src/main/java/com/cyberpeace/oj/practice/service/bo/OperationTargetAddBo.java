package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实操目标新增博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class OperationTargetAddBo {

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "镜像id")
	private String nodeId;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value = "镜像名称")
	private String nodeName;

	/**
	 * 附件id
	 */
	@ApiModelProperty(value = "附件id")
	private String attachmentId;

	/**
	 * node 命令
	 */
	@ApiModelProperty(value = "节点命令")
	private String nodeCommand;
}
