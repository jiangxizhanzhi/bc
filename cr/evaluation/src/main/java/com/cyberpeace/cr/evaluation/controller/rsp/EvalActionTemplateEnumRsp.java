package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalActionTemplateEnumRsp implements Serializable {

	@ApiModelProperty("动作类型")
	private List<CodeNameRsp> actionType;

	@ApiModelProperty("级别")
	private List<CodeNameRsp> level;
}
