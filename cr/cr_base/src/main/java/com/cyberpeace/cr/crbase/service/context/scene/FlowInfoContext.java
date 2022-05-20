package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowInfoContext implements Serializable {

	private String flowId;

	private String sourceIp;
}
