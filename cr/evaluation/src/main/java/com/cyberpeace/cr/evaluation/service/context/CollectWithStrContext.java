package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CollectWithStrContext extends EvalCollection {

	@ApiModelProperty(value = "变量项字符串")
	@NotNull
	private String str;

}