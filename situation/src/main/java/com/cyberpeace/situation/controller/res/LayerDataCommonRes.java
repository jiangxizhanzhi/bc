package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.node.NodeProtocolContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 跨层-通用数据返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class LayerDataCommonRes implements Serializable {

	/**
	 * 节点地理分布
	 */
	@ApiModelProperty("节点地理分布")
	private List<String> geoDistribution;

	/**
	 * 协议状态统计
	 */
	@ApiModelProperty("协议状态统计")
	private List<NodeProtocolContext> protocolStatus;

}
