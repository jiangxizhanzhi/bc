package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebTheoryListReq extends AdminTheoryListReq {

	@ApiModelProperty("是否解决")
	private Boolean solved;

}
