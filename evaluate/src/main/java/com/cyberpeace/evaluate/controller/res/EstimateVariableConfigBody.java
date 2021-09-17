package com.cyberpeace.evaluate.controller.res;

import java.io.Serializable;

import lombok.Data;

@Data
public class EstimateVariableConfigBody implements Serializable {

	/**变量项id */
	private String estimateVariableId;

	/**采集器id */
	private String collectId;

	/**主机id */
	private String hostId;
}
