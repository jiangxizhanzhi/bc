package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IpBo implements Serializable {

	@ApiModelProperty(value = "IP")
	private String ip;

}
