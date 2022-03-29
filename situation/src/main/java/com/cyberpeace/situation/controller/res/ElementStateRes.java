package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.mp.MpEventAggr;
import com.cyberpeace.integratation.model.mp.MpFlowTrend;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.model.mp.MpRiskCount;

import lombok.Data;

@Data
public class ElementStateRes implements Serializable {

	private MpEventAggr eventAggr;

	private List<MpFlowTrend> flowTrend;

	private MpHostState hostState;

	private MpRiskCount riskCount;
}
