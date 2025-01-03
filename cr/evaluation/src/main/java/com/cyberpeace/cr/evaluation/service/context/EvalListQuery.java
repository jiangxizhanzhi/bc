package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 评估列表查询
 *
 * @author SN
 * @date 2024/11/08
 *//*
 * 前台使用的查询类
 */
@ApiModel
@Getter
@Setter
public class EvalListQuery extends PageReqBase {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "算法语言 0:python,1:java")
	private Integer algorithm_language;

	/**
	 * id列表
	 */
	private List<String> ids;

}