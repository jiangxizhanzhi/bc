package com.cyberpeace.bs.service.bo.demo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SimulatorAddReq implements Serializable {

	private String userModelId;

	private String taskName;

	private String deviceId;

	private String projectId;

	private String environmentId;

	private List<String> flowChartIds;
}
