package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideHistoryRsp implements Serializable {

	@ApiModelProperty("事件名称")
	private String eventName;

	@ApiModelProperty("触发类型")
	private String triggerType;

	@ApiModelProperty("触发类型code")
	private Integer triggerCode;

	@ApiModelProperty("动作类型")
	private String actionType;

	@ApiModelProperty("动作类型code")
	private Integer actionCode;

	@ApiModelProperty("攻防类型")
	private List<String> category;

	@ApiModelProperty("攻防类型code")
	private List<Integer> categoryCode;

	@ApiModelProperty("源节点名称")
	private String sourceName;

	@ApiModelProperty("源节点IP")
	private String sourceIp;

	@ApiModelProperty("源节点端口")
	private String sourcePort;

	@ApiModelProperty("源节点id")
	private String sourceId;

	@ApiModelProperty("目的节点名称")
	private String targetName;

	@ApiModelProperty("目的节点IP")
	private String targetIp;

	@ApiModelProperty("目的节点端口")
	private String targetPort;

	@ApiModelProperty("目的节点id")
	private String targetId;

	@ApiModelProperty("结果")
	private Integer result;

	@ApiModelProperty("归属角色")
	private Integer role;

	@ApiModelProperty("时间")
	private Date createTime;

	public GuideHistoryRsp(String eventName, String triggerType, String actionType, List<String> category) {
		this.eventName = eventName;
		this.triggerType = triggerType;
		this.actionType = actionType;
		this.category = category;
	}
}
