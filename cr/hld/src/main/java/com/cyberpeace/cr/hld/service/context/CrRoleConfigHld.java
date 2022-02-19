package com.cyberpeace.cr.hld.service.context;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色配置
 */
@Data
public class CrRoleConfigHld implements Serializable {

	private List<CrRoleConfig> whiteRoles;

	private List<CrRoleConfig> redRoles;

	private List<CrRoleConfig> blueRoles;
}
