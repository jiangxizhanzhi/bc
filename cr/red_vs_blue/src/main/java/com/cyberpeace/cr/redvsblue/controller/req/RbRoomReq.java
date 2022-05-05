package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbRoomReq implements Serializable {

	@ApiModelProperty(required = true)
	@NotNull
	private String template;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer category;

	@ApiModelProperty(required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(required = true)
	@NotNull
	private String preSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private String trainSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer difficult;

	@ApiModelProperty(required = true)
	@NotNull
	private Date startTime;

	@ApiModelProperty(required = true)
	@NotNull
	private Date endTime;

	@ApiModelProperty(required = true)
	@NotNull
	private String description;

	@ApiModelProperty(required = true)
	@NotNull
	private String roleConfig;

	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	@ApiModelProperty(value = "LOGO")
	private String logo;

	public void validate() {
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}