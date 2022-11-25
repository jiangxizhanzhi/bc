package com.cyberpeace.assess.controller.req;


import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AssessMateriaFilterReq extends PageReqBase {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("案例id")
	@NotBlank(message = AssessErrorCode.CASE_ID_EMPTY)
	private String caseId;
}
