package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实操目标新增博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Data
public class OperationTargetAddBo {

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "镜像id")
	private String nodeId;

	/**
	 * 节点名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "镜像名称")
	private String nodeName;

	/**
	 * 附件id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "附件id")
	private String attachmentId;

	/**
	 * node 命令
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "节点命令")
	private String nodeCommand;
}
