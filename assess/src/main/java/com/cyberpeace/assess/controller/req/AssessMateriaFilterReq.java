package com.cyberpeace.assess.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.assess.constant.AssessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssessMateriaFilterReq extends AssessMaterialAddReq {

	@ApiModelProperty("名称")
	private String name;
}
