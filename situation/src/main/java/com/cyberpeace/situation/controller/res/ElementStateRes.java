package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.mp.MpEventAggr;
import com.cyberpeace.integratation.model.mp.MpFlowTrend;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.model.mp.MpRiskCount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ElementStateRes implements Serializable {

	@ApiModelProperty("统计信息")
	private MpEventAggr eventAggr;

	@ApiModelProperty("流量趋势")
	private List<MpFlowTrend> flowTrend;

	@ApiModelProperty("主机状态")
	private MpHostState hostState;

	@ApiModelProperty("风险统计")
	private MpRiskCount riskCount;
}
