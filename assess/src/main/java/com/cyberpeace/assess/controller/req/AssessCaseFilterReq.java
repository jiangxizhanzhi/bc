package com.cyberpeace.assess.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AssessCaseFilterReq extends PageReqBase {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("标签")
	private String tag;
}
