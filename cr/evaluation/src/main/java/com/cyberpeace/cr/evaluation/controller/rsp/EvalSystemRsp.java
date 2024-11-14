package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalSystemRsp {

	/**
	 * 评估体系Id
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 连接线
	 */
	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	/**
	 * 一级指标列表
	 */
	@ApiModelProperty(value = "一级指标列表", required = true)
	@NotNull
	private List<IndexContext> firstIndexes;

	/**
	 * 二级指标列表
	 */
	@ApiModelProperty(value = "二级指标列表", required = true)
	@NotNull
	private List<IndexContext> secondIndexes;

	/**
	 * 三级指标列表
	 */
	@ApiModelProperty(value = "三级指标列表", required = true)
	@NotNull
	private List<IndexContext> thirdIndexes;

}