package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.scene.FlowInfoContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowsBatchOpsReq extends FlowsOpsReq implements Serializable {

	@ApiModelProperty("流量id列表")
	private List<FlowInfoContext> flows;

}
