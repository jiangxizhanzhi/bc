package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估算法列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalAlgorithmListReq extends PageReqBase implements Serializable {

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "算法类型", required = false)
	private Integer type;
}
