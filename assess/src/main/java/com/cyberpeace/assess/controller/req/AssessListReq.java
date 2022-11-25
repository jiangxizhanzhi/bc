package com.cyberpeace.assess.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssessListReq extends PageReqBase {

	@ApiModelProperty("评估数据类型。1：输入，2：结果")
	Integer type;

	@ApiModelProperty("标签id")
	String tag;

}
