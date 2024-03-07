package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.IndexContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EvalSystemCopyReq {

	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

}