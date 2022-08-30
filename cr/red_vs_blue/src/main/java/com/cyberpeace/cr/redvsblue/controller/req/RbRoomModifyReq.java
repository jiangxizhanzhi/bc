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

	@ApiModelProperty(value = "模板id")
	private String template;

	@ApiModelProperty(value = "训练类别")
	private Integer category;

	@ApiModelProperty(value = "房间名称")
	private String name;

	@ApiModelProperty(value = "前置技能")
	private String preSkill;

	@ApiModelProperty(value = "训练目标")
	private String trainSkill;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "角色配置")
	private String roleConfig;

	@ApiModelProperty(value = "LOGO")
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