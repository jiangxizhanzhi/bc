package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板id响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTemplateIdRsp implements Serializable {

	/**
	 * 测评靶场模板id
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String templateId;

	public EvalTemplateIdRsp(String templateId) {
		this.templateId = templateId;
	}

}
