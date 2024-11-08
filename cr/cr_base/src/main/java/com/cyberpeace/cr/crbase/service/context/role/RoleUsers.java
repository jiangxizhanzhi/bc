package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位用户
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel("查询实例列表时包含所有用户的角色")
@Data
public class RoleUsers {

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;

	/**
	 * 岗位群
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 用户
	 */
	@ApiModelProperty(value = "用户列表")
	private List<String> users;

	/**
	 * 用户信息
	 */
	private List<UserData> userInfos;

}
