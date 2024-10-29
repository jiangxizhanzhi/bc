package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TargetBo implements Serializable {

	@ApiModelProperty("靶标id")
	private String nodeId;

	@ApiModelProperty("靶标名称")
	private String nodeName;

//	@ApiModelProperty("flag")
//	private String flag;
	@ApiModelProperty("附件id")
	private String attachmentId;

	@ApiModelProperty("附件名称")
	private String fileName;

	@ApiModelProperty("附件url")
	private String fileUrl;

	@ApiModelProperty("命令")
	private String nodeCommand;
}
