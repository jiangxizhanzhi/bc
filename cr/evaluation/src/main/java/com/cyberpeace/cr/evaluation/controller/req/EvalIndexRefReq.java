package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 评估指数裁判请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalIndexRefReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotEmpty
	private String resourceId;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	@ApiModelProperty("等级")
	private Integer level;

	/**
	 * 专家
	 *
	 * @see EvalRecordSpecialistContext
	 */
	@ApiModelProperty("指标项专家评分配置")
	private EvalRecordSpecialistContext specialist;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("评分方式 1系统 2 专家")
	private Integer type;

	/**
	 * 装置id
	 *
	 * @see String
	 */
	@ApiModelProperty("系统评分时 需要节点id")
	private String deviceId;

	/**
	 * 探针id
	 *
	 * @see String
	 */
	@ApiModelProperty("系统评分时 需要探针id")
	private String probeId;



}