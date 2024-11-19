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
 * 元素状态结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ElementStateRes implements Serializable {

	/**
	 * 统计信息
	 *
	 * @see MpEventAggr
	 */
	@ApiModelProperty("统计信息")
	private MpEventAggr eventAggr;

	/**
	 * 流量趋势
	 *
	 * @see List<MpFlowTrend>
	 */
	@ApiModelProperty("流量趋势")
	private List<MpFlowTrend> flowTrend;

	/**
	 * 主机状态
	 *
	 * @see MpHostState
	 */
	@ApiModelProperty("主机状态")
	private MpHostState hostState;

	/**
	 * 风险统计
	 *
	 * @see MpRiskCount
	 */
	@ApiModelProperty("风险统计")
	private MpRiskCount riskCount;
}
