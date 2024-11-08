package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 评估指数裁判请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalIndexRefReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotEmpty
	private String resourceId;

	/**
	 * 水平
	 */
	@ApiModelProperty("等级")
	private Integer level;

	/**
	 * 专家
	 */
	@ApiModelProperty("指标项专家评分配置")
	private EvalRecordSpecialistContext specialist;

	/**
	 * 类型
	 */
	@ApiModelProperty("评分方式 1系统 2 专家")
	private Integer type;

	/**
	 * 装置id
	 */
	@ApiModelProperty("系统评分时 需要节点id")
	private String deviceId;

	/**
	 * 探针id
	 */
	@ApiModelProperty("系统评分时 需要探针id")
	private String probeId;



}