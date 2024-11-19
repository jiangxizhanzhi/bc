package com.cyberpeace.rtks.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 知识点列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
public class KnowledgeListReq extends PageReqBase {

	/**
	 * 类别ID
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "category_id", value = "类别ID")
	@Getter
	private String categoryId;

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

}
