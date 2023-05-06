package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.flow.FlowStatisticsContext;
import com.cyberpeace.integratation.model.situation.flow.FlowTrendContext;
import com.cyberpeace.integratation.model.situation.flow.NodeFlowRankContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 跨层-传输层数据返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class LayerDataTransportRes implements Serializable {

	/**
	 * 实时流量统计
	 */
	@ApiModelProperty("实时流量统计")
	private FlowStatisticsContext flowStatistics;

	/**
	 * 节点流量排名-TOP5
	 */
	@ApiModelProperty("节点流量排名-TOP5")
	private List<NodeFlowRankContext> nodeFlowRank;

	/**
	 * 端口流量趋势
	 */
	@ApiModelProperty("端口流量趋势")
	private FlowTrendContext flowTrend;

}
