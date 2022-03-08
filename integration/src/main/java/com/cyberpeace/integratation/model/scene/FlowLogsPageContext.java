package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowLogsPageContext implements Serializable {

	private String id;

	private String startTime;

	private String endTime;

}
