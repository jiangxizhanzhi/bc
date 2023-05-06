package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.mp.MpEventAggr;
import com.cyberpeace.integratation.model.mp.MpFlowTrend;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.model.mp.MpRiskCount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点状态返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ElementStateRes implements Serializable {

	/**
	 * 统计信息
	 */
	@ApiModelProperty("统计信息")
	private MpEventAggr eventAggr;

	/**
	 * 流量趋势
	 */
	@ApiModelProperty("流量趋势")
	private List<MpFlowTrend> flowTrend;

	/**
	 * 主机状态
	 */
	@ApiModelProperty("主机状态")
	private MpHostState hostState;

	/**
	 * 风险统计
	 */
	@ApiModelProperty("风险统计")
	private MpRiskCount riskCount;
}
