package com.cyberpeace.rtks.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class SkillListReq extends PageReqBase {

	@ApiModelProperty(name = "system_id", value = "体系ID")
	@Getter
	private String systemId;

	@ApiModelProperty(name = "category_id", value = "类别ID")
	@Getter
	private String categoryId;

	public void setSystem_id(String systemId) {
		this.systemId = systemId;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

}
