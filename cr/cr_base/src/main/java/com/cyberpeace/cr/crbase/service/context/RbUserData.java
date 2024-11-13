package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝用户数据
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class RbUserData implements Serializable {

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 代表名称
	 */
	@ApiModelProperty(value = "用户姓名")
	private String repName;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "用户编号")
	private String resourceId;

	/**
	 * 组名称
	 */
	@ApiModelProperty(value = "用户组名")
	private String groupName;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;
}
