package com.cyberpeace.integratation.model.scene;

import java.util.List;

import lombok.Data;

@Data
public class FlowBatchOpsContext extends NodeOpsContext {

	private Integer run_type;

	private List<FlowInfoContext> flows;

	public void setRunType(Integer runType) {
		this.run_type = runType;
	}
}
