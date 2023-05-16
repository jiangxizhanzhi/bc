package com.cyberpeace.cr.crbase.service.context.wiki;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WikiCreateReq implements Serializable {

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "图标")
	private String logo;

	public void validate() {
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
//		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}
