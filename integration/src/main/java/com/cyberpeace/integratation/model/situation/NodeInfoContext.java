package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeInfoContext implements Serializable {

	@ApiModelProperty(value = "节点Id")
	private String nodeId;

	@ApiModelProperty(value = "节点IP")
	private String nodeIp;

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	@ApiModelProperty(value = "节点MAC")
	private String nodeMac;

	@ApiModelProperty(value = "重要程度")
	private String importance;

	@ApiModelProperty(value = "所属阵营")
	private String group;

	@ApiModelProperty(value = "节点关键度")
	private String criticalDegree;

	@ApiModelProperty(value = "内容列表")
	private List<DynamicContext> dynamicList;

	@ApiModelProperty(value = "归属平台")
	private String platform;

	@ApiModelProperty(value = "安全状态")
	private String safetyState;

	@ApiModelProperty(value = "脆弱性")
	private String weak;

	@ApiModelProperty(value = "节点类型")
	private String nodeType;

	@ApiModelProperty(value = "工作状态")
	private String workStatus;

	@ApiModelProperty(value = "所属网络")
	private String network;

	@ApiModelProperty(value = "物理位置")
	private String location;

	@ApiModelProperty(value = "受威胁程度")
	private String threatenedDegree;

	@ApiModelProperty(value = "桥接状态 0-无桥接 1-桥接点")
	private Integer bridgeStatus;
}