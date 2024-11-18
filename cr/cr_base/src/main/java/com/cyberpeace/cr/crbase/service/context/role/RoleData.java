package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RoleData<T> {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色名")
	private String name;

	/**
	 * 任务列表
	 *
	 * @see List<MissionSchemeObject<T>>
	 */
	@ApiModelProperty(value = "任务列表")
	private List<MissionSchemeObject<T>> missionsList;

	/**
	 * 用户
	 *
	 * @see List<NameElement>
	 */
	@ApiModelProperty(value = "用户列表")
	private List<NameElement> users;

	/**
	 * 用户id列表
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户id")
	private String userIds;

}
