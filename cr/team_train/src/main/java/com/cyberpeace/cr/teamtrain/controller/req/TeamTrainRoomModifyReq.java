package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainRoomModifyReq {

	@ApiModelProperty(value = "实例编号", required = true)
	@NotNull
	private String resourceId;

	@ApiModelProperty(value = "类别")
	private Integer category;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "角色配置")
	private String roleConfig;

	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	@ApiModelProperty(value = "LOGO")
	private String logo;

	public void validate() {
		if (name != null) {
			CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (description != null) {
			CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
	}
}
