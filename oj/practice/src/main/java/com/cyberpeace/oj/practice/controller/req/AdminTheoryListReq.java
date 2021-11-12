package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminTheoryListReq extends PageReqBase {

	@ApiModelProperty("习题集")
	private String practiceSetId;

	@ApiModelProperty("方向")
	private String categoryId;

}
