package com.cyberpeace.assess.controller.req;

import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class AssessCaseAddReq implements Serializable {

	@ApiModelProperty(value = "评估案例名称")
	@NotBlank(groups = { Create.class, Update.class }, message = AssessErrorCode.NAME_MISSING)
	private String name;

	@ApiModelProperty(value = "概述")
	private String overview;

	@ApiModelProperty(value = "封面")
	private String cover;

	@ApiModelProperty(value = "标签")
	private List<String> tag;
}
