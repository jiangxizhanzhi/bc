package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalCloudTemplateEnumRsp implements Serializable {

	@ApiModelProperty("试验类型")
	private List<CodeNameRsp> testType;

	@ApiModelProperty("云类型")
	private List<CodeNameRsp> cloudType;
}
