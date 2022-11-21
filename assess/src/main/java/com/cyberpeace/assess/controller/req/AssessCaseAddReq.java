package com.cyberpeace.assess.controller.req;

import com.cyberpeace.assess.constant.AssessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class AssessCaseAddReq implements Serializable {

	@ApiModelProperty(value = "评估案例名称")
	@NotNull(message = AssessErrorCode.NAME_MISSING)
	private String name;

	@ApiModelProperty(value = "概述")
	private String overview;

	@ApiModelProperty(value = "封面")
	@NotNull(message = AssessErrorCode.COVER_MISSING)
	private String cover;

	@ApiModelProperty(value = "标签")
	@NotEmpty(message = AssessErrorCode.TAG_MISSING)
	private String tag;
}
