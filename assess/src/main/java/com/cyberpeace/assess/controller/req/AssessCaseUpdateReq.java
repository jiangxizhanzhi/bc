package com.cyberpeace.assess.controller.req;

import com.cyberpeace.assess.constant.AssessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class AssessCaseUpdateReq extends AssessCaseAddReq {

	@ApiModelProperty(value = "资源id")
	@NotNull(message = AssessErrorCode.RESOURCE_ID_MISSING)
	private String resourceId;
}
