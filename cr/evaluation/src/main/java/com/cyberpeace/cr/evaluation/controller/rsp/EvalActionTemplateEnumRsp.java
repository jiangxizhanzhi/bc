package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalActionTemplateEnumRsp implements Serializable {

	@ApiModelProperty("告警级别")
	private List<CodeNameRsp> level;

	@ApiModelProperty("攻击类型")
	private List<EvalAttackTypeRsp> attackType;

	@ApiModelProperty("攻击结果")
	private List<CodeNameRsp> result;
}
