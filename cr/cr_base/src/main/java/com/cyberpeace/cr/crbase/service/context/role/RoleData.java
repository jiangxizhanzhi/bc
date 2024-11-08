package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class RoleData<T> {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "角色id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "角色名")
	private String name;

	/**
	 * 任务列表
	 */
	@ApiModelProperty(value = "任务列表")
	private List<MissionSchemeObject<T>> missionsList;

	/**
	 * 用户
	 */
	@ApiModelProperty(value = "用户列表")
	private List<NameElement> users;

	/**
	 * 用户id列表
	 */
	@ApiModelProperty(value = "用户id")
	private String userIds;

}
