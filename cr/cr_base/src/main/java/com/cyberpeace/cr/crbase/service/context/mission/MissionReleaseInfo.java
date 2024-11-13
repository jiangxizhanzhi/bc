package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.UserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务下发信息
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class MissionReleaseInfo extends Mission {

	/**
	 * 完成用户列表
	 */
	@ApiModelProperty(value = "已完成用户列表")
	private List<UserData> finishUserList;

	/**
	 * 未完成用户列表
	 */
	@ApiModelProperty(value = "未完成用户列表")
	private List<UserData> notFinishUserList;

	/**
	 * 是否下发
	 */
	@ApiModelProperty(value = "是否下发")
	private Boolean isRelease;

	/**
	 * 是否动态
	 */
	@ApiModelProperty(value = "是否动态新增")
	private Boolean isDynamic;
}
