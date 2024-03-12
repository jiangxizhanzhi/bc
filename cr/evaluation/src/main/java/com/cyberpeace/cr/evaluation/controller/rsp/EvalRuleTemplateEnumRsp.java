package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRuleTemplateEnumRsp implements Serializable {

	@ApiModelProperty("状态")
	private List<CodeNameRsp> status;

	@ApiModelProperty("等级")
	private List<CodeNameRsp> grade;

	@ApiModelProperty("布尔关系")
	private List<CodeNameRsp> booleanRelation;
}
