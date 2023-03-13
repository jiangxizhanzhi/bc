package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.NodeTypeStatisticsContext;
import com.cyberpeace.integratation.model.situation.node.NodeCriticalDegreeContext;
import com.cyberpeace.integratation.model.situation.node.StatusStatisticsContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LayerDataNetRes implements Serializable {

	@ApiModelProperty("节点状态统计")
	private List<StatusStatisticsContext> nodeStatus;

	@ApiModelProperty("子网数量统计")
	private Integer netNum;

	@ApiModelProperty("节点类型分布")
	private List<NodeTypeStatisticsContext> nodeTypeDistribute;

	@ApiModelProperty("节点关键度-TOP5")
	private List<NodeCriticalDegreeContext> nodeCriticalDegree;

}
