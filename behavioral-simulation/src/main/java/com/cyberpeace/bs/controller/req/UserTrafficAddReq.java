package com.cyberpeace.bs.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserTrafficAddReq implements Serializable {

	@ApiModelProperty(value = "用户行为模拟id")
	private String userBehaviorId;
}
