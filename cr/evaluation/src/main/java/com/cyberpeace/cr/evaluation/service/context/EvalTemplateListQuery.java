package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty("测评类型")
	private Integer categoryCode;

}
