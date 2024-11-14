package com.cyberpeace.rtks.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class KnowledgeListReq extends PageReqBase {

	/**
	 * 类别ID
	 */
	@ApiModelProperty(name = "category_id", value = "类别ID")
	@Getter
	private String categoryId;

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

}
