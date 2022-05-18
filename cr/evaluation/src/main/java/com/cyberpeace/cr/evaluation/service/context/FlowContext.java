package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowContext implements Serializable {

	private String flowId;

	private String sourceIp;
}
