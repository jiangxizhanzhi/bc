package com.cyberpeace.cr.hld.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色配置
 */
@Data
public class HldRolesConfig implements Serializable {

	private List<HldRoleConfig> whiteRoles;

	private List<HldRoleConfig> redRoles;

	private List<HldRoleConfig> blueRoles;
}
