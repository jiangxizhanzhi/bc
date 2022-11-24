package com.cyberpeace.assess.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.common.baseinterface.Update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssessCaseUpdateReq extends AssessCaseAddReq {

	@ApiModelProperty(value = "资源id")
	@NotBlank(groups = { Update.class }, message = AssessErrorCode.RESOURCE_ID_MISSING)
	private String resourceId;
}
