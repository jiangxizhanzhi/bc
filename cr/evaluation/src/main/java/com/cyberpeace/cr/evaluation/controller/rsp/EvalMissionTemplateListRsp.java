package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalMissionTemplateListRsp implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String name;

}
