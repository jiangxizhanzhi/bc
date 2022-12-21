package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlowLogsPageContext implements Serializable {

	private String id;

	private String startTime;

	private String endTime;

	private Integer perPage;

	private Integer page;

	private String sort;

}
