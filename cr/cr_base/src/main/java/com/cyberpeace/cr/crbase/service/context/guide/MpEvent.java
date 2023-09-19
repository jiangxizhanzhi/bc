package com.cyberpeace.cr.crbase.service.context.guide;

import com.cyberpeace.constant.BaseI18n;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class MpEvent implements Serializable {

	@ApiModelProperty("动作类型：attack:攻击,defense:防御")
	private String actionType;

	@ApiModelProperty("攻防类型名称")
	private List<String> attCkNames;

	@ApiModelProperty("攻防类型")
	private List<String> attCks;

	@ApiModelProperty("事件名称")
	private String eventName;

	@ApiModelProperty("场景id")
	private String id;

	@ApiModelProperty("源节点id")
	private String sourceNodeId;

	@ApiModelProperty("源节点ip")
	private String sourceNodeIp;

	@ApiModelProperty("源节点名称")
	private String sourceNodeName;

	@ApiModelProperty("源节点端口")
	private String sourceNodePort;

	@ApiModelProperty("目的点id")
	private String targetNodeId;

	@ApiModelProperty("目的点ip")
	private String targetNodeIp;

	@ApiModelProperty("目的点名称")
	private String targetNodeName;

	@ApiModelProperty("目的点端口")
	private String targetNodePort;

	@ApiModelProperty("触发类型:host:主机采集,flow:流量采集")
	private String triggerType;

}
