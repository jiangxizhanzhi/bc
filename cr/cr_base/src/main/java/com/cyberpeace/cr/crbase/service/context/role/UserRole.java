package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@ApiModel("单个用户角色")

public class UserRole {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String name;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("用户头像")
	private String logo;

	/**
	 * 角色名称
	 *
	 * @see String
	 */
	@ApiModelProperty("角色")
	private String roleName;

	/**
	 * 角色描述
	 *
	 * @see String
	 */
	@ApiModelProperty("角色描述")
	private String roleDesc;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("角色组：4红方 5蓝方")
	private Integer type;

	public static String getName(Function<UserRole,String> function, List<UserRole> userRoleList) {
		if (CollectionUtils.isEmpty(userRoleList)) {
			return "";
		}
		return userRoleList.stream().map(function).collect(Collectors.joining(","));
	}
}
