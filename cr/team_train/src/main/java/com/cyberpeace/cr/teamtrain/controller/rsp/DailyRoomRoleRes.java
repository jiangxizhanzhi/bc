package com.cyberpeace.cr.teamtrain.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DailyRoomRoleRes implements Serializable {

	@ApiModelProperty(value = "组织名称")
	private String name;

	@ApiModelProperty(value = "组织类型")
	private String type;

	@ApiModelProperty(value = "所有岗位(包含教员)，单独查时使用")
	private List<TeamTrainTemplateRoleRsp> children;

	@ApiModelProperty(value = "自己岗位，基本信息，查列表会查询其他学员角色的内容列表")
	private  BaseRole trainingRoleData;
}
