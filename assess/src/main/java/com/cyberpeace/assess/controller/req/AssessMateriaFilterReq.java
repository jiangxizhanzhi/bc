package com.cyberpeace.assess.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AssessMateriaFilterReq extends PageReqBase {

	@ApiModelProperty("名称")
	private String name;
}
