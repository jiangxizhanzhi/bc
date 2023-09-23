package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.UserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionReleaseInfo extends Mission {

	@ApiModelProperty(value = "已完成用户列表")
	private List<UserData> finishUserList;

	@ApiModelProperty(value = "未完成用户列表")
	private List<UserData> notFinishUserList;

	@ApiModelProperty(value = "是否下发")
	private Boolean isRelease;

	@ApiModelProperty(value = "是否动态新增")
	private Boolean isDynamic;
}
