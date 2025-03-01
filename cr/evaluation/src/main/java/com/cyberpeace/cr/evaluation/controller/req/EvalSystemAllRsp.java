package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class EvalSystemAllRsp extends EvalSystemRsp {

	@ApiModelProperty(value = "四级指标列表", required = true)
	@NotNull
	private List<EvalAggregationContext> fourthIndexes = new ArrayList<>();

	@ApiModelProperty(value = "五级指标列表", required = true)
	@NotNull
	private List<EvalCollectionContext> fifthIndexes = new ArrayList<>();

}