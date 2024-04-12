package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalNodeOperateContentRsp extends EvalMissionOperateContentRsp {

	@ApiModelProperty(value = "操作内容")
	private String content;


}
