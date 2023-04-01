package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * 前台使用的查询类
 */
@ApiModel
@Data
public class EvalListQuery extends PageReqBase {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "类型")
	private Integer type;

	private List<String> ids;

	private Integer attribute;

}