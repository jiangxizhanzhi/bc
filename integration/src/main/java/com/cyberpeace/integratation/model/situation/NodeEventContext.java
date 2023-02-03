package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.integratation.constant.NodeWorkStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeEventContext implements Serializable {

	@ApiModelProperty("节点ID")
	private String nodeId;

	@ApiModelProperty("工作状态 0-工作中 1-离线")
	private Integer workStatus;

	public NodeEventContext() {}

	public NodeEventContext(String nodeId, Integer workStatus) {
		this.nodeId = nodeId;
		this.workStatus = workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = EnumUtils.getEnumCodeByDesc(workStatus, NodeWorkStatus.class);
	}

}
