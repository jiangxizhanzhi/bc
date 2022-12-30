package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeMigrationReq implements Serializable {

	@ApiModelProperty(value = "分靶场ID")
	private String subRangeId;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@ApiModelProperty(value = "节点ID")
	private String nodeId;

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	@ApiModelProperty(value = "节点IP")
	private String nodeIp;

	@ApiModelProperty(value = "源服务器")
	private String source;

	@ApiModelProperty(value = "目标服务器")
	private String target;

	@ApiModelProperty(value = "迁移规律")
	private String law;
}
