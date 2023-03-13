package com.cyberpeace.integratation.model.situation.flow;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowStatisticsContext implements Serializable {

	@ApiModelProperty("总流量（M）")
	private Integer total;

	@ApiModelProperty("TCP流量（M）")
	private Integer tcp;

	@ApiModelProperty("UDP流量（M）")
	private Integer udp;

}
