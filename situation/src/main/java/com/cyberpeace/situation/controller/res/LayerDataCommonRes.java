package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.node.NodeProtocolContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LayerDataCommonRes implements Serializable {

	@ApiModelProperty("节点地理分布")
	private List<String> geoDistribution;

	@ApiModelProperty("协议状态统计")
	private List<NodeProtocolContext> protocolStatus;

}
