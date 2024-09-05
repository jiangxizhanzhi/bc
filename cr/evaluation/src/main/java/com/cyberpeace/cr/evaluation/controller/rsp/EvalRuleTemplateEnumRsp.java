package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.StrCodeNameRsp;
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
	private List<StrCodeNameRsp> operator;

	@ApiModelProperty("聚合类型")
	private List<StrCodeNameRsp> aggregation;

	@ApiModelProperty("自定义日志搜索")
	private List<StrCodeNameRsp> condition;
}
