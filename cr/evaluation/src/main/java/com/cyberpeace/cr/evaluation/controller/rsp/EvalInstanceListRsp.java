package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.EvalInstanceSlimContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EvalInstanceListRsp {

	@ApiModelProperty(value = "当前测评实例列表")
	private List<EvalInstanceSlimContext> instanceList;

	@ApiModelProperty(value = "当前测评实例")
	private String currentInstanceId;
}
