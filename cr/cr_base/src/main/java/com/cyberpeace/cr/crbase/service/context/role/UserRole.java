package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户岗位
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel("单个用户角色")

public class UserRole {

	/**
	 * 资源id
	 */
	@ApiModelProperty("用户id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("用户名")
	private String name;

	/**
	 * 图标
	 */
	@ApiModelProperty("用户头像")
	private String logo;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty("角色")
	private String roleName;

	/**
	 * 岗位描述
	 */
	@ApiModelProperty("角色描述")
	private String roleDesc;

	/**
	 * 类型
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
