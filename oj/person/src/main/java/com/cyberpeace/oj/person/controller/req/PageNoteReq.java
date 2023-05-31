package com.cyberpeace.oj.person.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageNoteReq extends PageReqBase {

	@ApiModelProperty("业务类型")
	private Integer businessType;

	@ApiModelProperty("题型")
	private Integer practiceType;
}
