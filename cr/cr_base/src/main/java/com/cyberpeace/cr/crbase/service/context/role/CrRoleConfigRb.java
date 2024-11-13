package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 红蓝后台模板的角色配置
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class CrRoleConfigRb implements Serializable {

	/**
	 * RED 角色
	 */
	private List<CrRoleConfig> redRoles;

	/**
	 * 蓝色角色
	 */
	private List<CrRoleConfig> blueRoles;
}
