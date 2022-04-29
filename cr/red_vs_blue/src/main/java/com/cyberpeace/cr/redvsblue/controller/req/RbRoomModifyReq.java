package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbRoomModifyReq implements Serializable {

	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;

	private String template;

	private Integer category;

	private String name;

	private String preSkill;

	private String trainSkill;

	private Integer difficult;

	private Date startTime;

	private Date endTime;

	private String description;

	private String roleConfig;

	private String logo;

	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	public void validate() {
		if (name != null) {
			CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (description != null) {
			CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
	}
}