package com.cyberpeace.cr.hld.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色配置
 */
@Data
public class CrRoleConfigHld implements Serializable {

	private List<HldRoleConfig> whiteRoles;

	private List<HldRoleConfig> redRoles;

	private List<HldRoleConfig> blueRoles;
}
