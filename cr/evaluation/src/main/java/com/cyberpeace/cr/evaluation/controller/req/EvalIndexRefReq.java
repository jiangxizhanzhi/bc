package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估编号裁判请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalIndexRefReq {

	/**
	 * 评估体系Id
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotEmpty
	private String resourceId;

	/**
	 * 等级
	 */
	@ApiModelProperty("等级")
	private Integer level;

	/**
	 * 指标项专家评分配置
	 */
	@ApiModelProperty("指标项专家评分配置")
	private EvalRecordSpecialistContext specialist;

	/**
	 * 评分方式 1系统 2 专家
	 */
	@ApiModelProperty("评分方式 1系统 2 专家")
	private Integer type;

	/**
	 * 系统评分时 需要节点id
	 */
	@ApiModelProperty("系统评分时 需要节点id")
	private String deviceId;

	/**
	 * 系统评分时 需要探针id
	 */
	@ApiModelProperty("系统评分时 需要探针id")
	private String probeId;



}