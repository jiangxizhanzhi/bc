package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NetworkInfoContext implements Serializable {

	@ApiModelProperty("节点数量")
	private Integer nodeCount;

	@ApiModelProperty("节点类型统计")
	private List<NodeTypeStatisticsContext> nodeTypeStatistics;

	@ApiModelProperty("发起攻击节点列表")
	private List<NodeAttackStatisticsContext> attackNodeList;

	@ApiModelProperty("遭受攻击节点列表")
	private List<NodeAttackedStatisticsContext> attackedNodeList;
}
