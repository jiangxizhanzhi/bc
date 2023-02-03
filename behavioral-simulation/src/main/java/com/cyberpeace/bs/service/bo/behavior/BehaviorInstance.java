package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BehaviorInstance extends LcBehavior implements Serializable {

	@ApiModelProperty(value = "用户名")
	private String userName;
}
