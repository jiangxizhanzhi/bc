package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRuleTemplateListReq extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "cloud_template_id", value = "云模板id")
	private String cloudTemplateId;

	@ApiModelProperty("等级")
	private Integer grade;

	@ApiModelProperty("状态")
	private Integer status;

	public void setCloud_template_id(String cloudTemplateId) {
		this.cloudTemplateId = cloudTemplateId;
	}
}
