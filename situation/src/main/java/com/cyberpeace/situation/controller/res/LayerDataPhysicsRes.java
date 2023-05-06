package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.TypeStatisticsContext;
import com.cyberpeace.integratation.model.situation.node.StatusStatisticsContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 跨层-物理层数据返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class LayerDataPhysicsRes implements Serializable {

	/**
	 * 节点地理分布
	 */
	@ApiModelProperty("节点地理分布")
	private List<String> geoDistribution;

	/**
	 * 链路类型分布
	 */
	@ApiModelProperty("链路类型分布")
	private List<TypeStatisticsContext> connectionTypeDistribute;

	/**
	 * 链路状态统计
	 */
	@ApiModelProperty("链路状态统计")
	private List<StatusStatisticsContext> connectionStatus;

}
