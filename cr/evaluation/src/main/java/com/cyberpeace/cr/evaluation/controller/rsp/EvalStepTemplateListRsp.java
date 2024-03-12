package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.controller.req.LoadSetReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalStepTemplateListRsp implements Serializable {

	@ApiModelProperty("步骤id")
	private String stepId;

	@ApiModelProperty("阶段名称")
	private String name;

	@ApiModelProperty("验证过程")
	private String process;

	@ApiModelProperty("载荷设置")
	private List<LoadSetReq> set;
}
