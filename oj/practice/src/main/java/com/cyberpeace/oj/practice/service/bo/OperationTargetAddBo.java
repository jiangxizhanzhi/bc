package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationTargetAddBo {

	@ApiModelProperty(value = "镜像id")
	private String nodeId;

	@ApiModelProperty(value = "镜像名称")
	private String nodeName;

	@ApiModelProperty(value = "附件id")
	private String attachmentId;

	@ApiModelProperty(value = "节点命令")
	private String nodeCommand;
}
