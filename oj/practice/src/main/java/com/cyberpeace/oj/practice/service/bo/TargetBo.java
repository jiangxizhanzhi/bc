package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 目标 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TargetBo implements Serializable {

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("靶标id")
	private String nodeId;

	/**
	 * 节点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("靶标名称")
	private String nodeName;

	/**
	 * 附件id
	 *
	 * @see String
	 */ //	@ApiModelProperty("flag")
//	private String flag;
	@ApiModelProperty("附件id")
	private String attachmentId;

	/**
	 * 文件名称
	 *
	 * @see String
	 */
	@ApiModelProperty("附件名称")
	private String fileName;

	/**
	 * 文件 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("附件url")
	private String fileUrl;

	/**
	 * node 命令
	 *
	 * @see String
	 */
	@ApiModelProperty("命令")
	private String nodeCommand;
}
