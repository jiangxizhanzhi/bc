package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 红蓝后台模板的角色配置
 */
@Data
public class CrRoleConfigRb implements Serializable {

	private List<CrRoleConfig> redRoles;

	private List<CrRoleConfig> blueRoles;
}
