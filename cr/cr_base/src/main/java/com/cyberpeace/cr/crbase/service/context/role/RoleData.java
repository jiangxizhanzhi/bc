package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色数据
 @author cyberpeace
 @date 2023/05/06
 */
@Data
public class RoleData<T> {

	@ApiModelProperty(value = "角色id")
	private String resourceId;

	@ApiModelProperty(value = "角色名")
	private String name;

	@ApiModelProperty(value = "任务列表")
	private List<MissionSchemeObject<T>> missionsList;

	@ApiModelProperty(value = "用户列表")
	private List<NameElement> users;

	@ApiModelProperty(value = "用户id")
	private String userIds;

}
