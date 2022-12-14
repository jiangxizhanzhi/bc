package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.integratation.model.situation.req.TaskBaseReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("平台ID")
	private String platformId;

}
