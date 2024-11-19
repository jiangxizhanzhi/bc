package com.cyberpeace.rtks.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 技能列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
public class SkillListReq extends PageReqBase {

	/**
	 * 体系ID
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "system_id", value = "体系ID")
	@Getter
	private String systemId;

	/**
	 * 类别ID
	 *
	 * @see String
	 */
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
