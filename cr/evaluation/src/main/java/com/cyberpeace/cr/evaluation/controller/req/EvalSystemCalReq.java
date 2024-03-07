package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.IndexContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EvalSystemCalReq {

	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	@ApiModelProperty(value = "三级指标列表", required = true)
	@NotNull
	private List<IndexContext> thirdIndexes;

}