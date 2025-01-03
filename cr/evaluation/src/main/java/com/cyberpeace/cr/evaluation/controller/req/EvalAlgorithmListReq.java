package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估算法列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalAlgorithmListReq extends PageReqBase implements Serializable {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "算法类型", required = false)
	private Integer type;

	@ApiModelProperty(value = "算法语言 0:python,1:java")
	private Integer algorithm_language;
}
