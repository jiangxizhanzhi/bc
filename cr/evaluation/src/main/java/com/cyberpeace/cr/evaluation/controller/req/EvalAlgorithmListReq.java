package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估算法列表请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalAlgorithmListReq extends PageReqBase implements Serializable {

	@ApiModelProperty(value = "算法类型", required = false)
	private Integer type;
}
