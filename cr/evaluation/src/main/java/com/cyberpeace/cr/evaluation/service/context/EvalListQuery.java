package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估列表查询
 *
 * @author SN
 * @date 2024/11/07
 *//*
 * 前台使用的查询类
 */
@ApiModel
@Data
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

	/**
	 * id列表
	 */
	private List<String> ids;

}