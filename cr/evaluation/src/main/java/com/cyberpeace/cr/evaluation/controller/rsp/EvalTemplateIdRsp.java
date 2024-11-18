package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板id响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalTemplateIdRsp implements Serializable {

	/**
	 * 模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String templateId;

	public EvalTemplateIdRsp(String templateId) {
		this.templateId = templateId;
	}

}
