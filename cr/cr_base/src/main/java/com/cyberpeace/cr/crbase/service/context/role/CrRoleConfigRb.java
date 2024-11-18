package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 红蓝后台模板的角色配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CrRoleConfigRb implements Serializable {

	/**
	 * RED 角色
	 *
	 * @see List<CrRoleConfig>
	 */
	private List<CrRoleConfig> redRoles;

	/**
	 * 蓝色角色
	 *
	 * @see List<CrRoleConfig>
	 */
	private List<CrRoleConfig> blueRoles;
}
