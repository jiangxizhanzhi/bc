package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * cr角色配置rb
 * 红蓝后台模板的角色配置
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CrRoleConfigRb implements Serializable {

	private List<CrRoleConfig> redRoles;

	private List<CrRoleConfig> blueRoles;
}
