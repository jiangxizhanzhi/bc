package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeConnectionContext implements Serializable {

	@ApiModelProperty(value = "链路ID")
	private String connectionId;

	@ApiModelProperty(value = "链路名称")
	private String connectionName;

	@ApiModelProperty(value = "链路类型")
	private String connectionType;

	@ApiModelProperty(value = "所属阵营")
	private String group;

	@ApiModelProperty(value = "关键度")
	private String criticalDegree;

	@ApiModelProperty(value = "带宽")
	private String bandwidth;

	@ApiModelProperty(value = "联通状态")
	private String unicomStatus;

	@ApiModelProperty(value = "所属网络")
	private String network;

}
