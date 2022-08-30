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

	@ApiModelProperty(required = true, value = "模板id")
	@NotNull
	private String template;

	@ApiModelProperty(required = true, value = "训练类别")
	@NotNull
	private Integer category;

	@ApiModelProperty(required = true, value = "房间名称")
	@NotBlank
	private String name;

	@ApiModelProperty(required = true, value = "前置技能")
	@NotNull
	private String preSkill;

	@ApiModelProperty(required = true, value = "训练目标")
	@NotNull
	private String trainSkill;

	@ApiModelProperty(required = true, value = "难度")
	@NotNull
	private Integer difficult;

	@ApiModelProperty(required = true, value = "开始时间")
	@NotNull
	private Date startTime;

	@ApiModelProperty(required = true, value = "结束时间")
	@NotNull
	private Date endTime;

	@ApiModelProperty(required = true, value = "描述")
	@NotNull
	private String description;

	@ApiModelProperty(required = true, value = "角色配置")
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