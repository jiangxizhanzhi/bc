package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 目标 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TargetBo implements Serializable {

	/**
	 * 节点id
	 */
	@ApiModelProperty("靶标id")
	private String nodeId;

	/**
	 * 节点名称
	 */
	@ApiModelProperty("靶标名称")
	private String nodeName;

	/**
	 * 附件id
	 *///	@ApiModelProperty("flag")
//	private String flag;
	@ApiModelProperty("附件id")
	private String attachmentId;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("附件名称")
	private String fileName;

	/**
	 * 文件 URL
	 */
	@ApiModelProperty("附件url")
	private String fileUrl;

	/**
	 * node 命令
	 */
	@ApiModelProperty("命令")
	private String nodeCommand;
}
