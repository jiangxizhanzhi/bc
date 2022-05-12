package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("测试靶场模板分页查询")
public class EvalTemplateListReq extends PageReqBase implements Serializable {

	@ApiModelProperty("测评类型")
	private Integer categoryCode;

}
