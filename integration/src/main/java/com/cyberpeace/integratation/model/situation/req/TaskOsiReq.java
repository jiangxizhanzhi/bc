package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskOsiReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("环境配置方案ID")
	private String envId;

	@ApiModelProperty("方案ID")
	private String schemeId;

}
