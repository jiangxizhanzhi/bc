package com.cyberpeace.integratation.model.situation.node;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeMigrationContext implements Serializable {

	@ApiModelProperty("节点ID")
	private String nodeId;

	@ApiModelProperty("节点IP")
	private String nodeIp;

	@ApiModelProperty("节点名称")
	private String nodeName;

	@ApiModelProperty("源服务器")
	private String source;

	@ApiModelProperty("目标服务器")
	private String target;

	@ApiModelProperty("迁移规则")
	private String law;

	@ApiModelProperty("迁移时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;
}
