package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.NodeTypeStatisticsContext;
import com.cyberpeace.integratation.model.situation.node.NodeCriticalDegreeContext;
import com.cyberpeace.integratation.model.situation.node.StatusStatisticsContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 跨层-网络层数据返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class LayerDataNetRes implements Serializable {

	/**
	 * 节点状态统计
	 */
	@ApiModelProperty("节点状态统计")
	private List<StatusStatisticsContext> nodeStatus;

	/**
	 * 子网数量统计
	 */
	@ApiModelProperty("子网数量统计")
	private Integer netNum;

	/**
	 * 节点类型分布
	 */
	@ApiModelProperty("节点类型分布")
	private List<NodeTypeStatisticsContext> nodeTypeDistribute;

	/**
	 * 节点关键度-TOP5
	 */
	@ApiModelProperty("节点关键度-TOP5")
	private List<NodeCriticalDegreeContext> nodeCriticalDegree;

}
