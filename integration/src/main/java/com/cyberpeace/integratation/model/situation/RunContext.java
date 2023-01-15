package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RunContext implements Serializable {

	@ApiModelProperty("评估ID")
	private String runId;

}
