package com.cyberpeace.cr.hld.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色配置
 */
@Data
public class HldRoleConfigBase extends HldRoleConfig implements Serializable {

	private String key;

	private String type;

	private List<HldRoleConfig> children;

}
