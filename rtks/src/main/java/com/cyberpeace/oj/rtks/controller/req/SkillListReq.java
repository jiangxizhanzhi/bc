package com.cyberpeace.oj.rtks.controller.req;

import com.cyberpeace.oj.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SkillListReq extends PageReqBase {

	@ApiModelProperty(name = "system_id", value = "体系ID")
	@Getter
	private String systemId;

	@ApiModelProperty(name = "categary_id", value = "类别ID")
	@Getter
	private String categaryId;

	@ApiModelProperty(value = "检索参数")
	@Getter
	@Setter
	private String pattern;

	public void setSystem_id(String systemId) {
		this.systemId = systemId;
	}

	public void setCategary_id(String categaryId) {
		this.categaryId = categaryId;
	}

}
