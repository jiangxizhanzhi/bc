package com.cyberpeace.oj.rtks.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class KnowledgeListReq extends PageReqBase {

	@ApiModelProperty(name = "category_id", value = "类别ID")
	@Getter
	private String categoryId;

	@ApiModelProperty(value = "检索参数")
	@Getter
	@Setter
	private String pattern;

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

}
