package com.cyberpeace.assess.controller.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AssessMateriaFilterReq implements Serializable {

	@ApiModelProperty("名称")
	private String name;
}
