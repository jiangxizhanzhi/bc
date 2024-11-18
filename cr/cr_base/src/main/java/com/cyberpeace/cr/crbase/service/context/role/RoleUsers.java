package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色用户
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@ApiModel("查询实例列表时包含所有用户的角色")
@Data
public class RoleUsers {

	/**
	 * 角色名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 用户
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "用户列表")
	private List<String> users;

	/**
	 * 用户信息
	 *
	 * @see List<UserData>
	 */
	private List<UserData> userInfos;

}
