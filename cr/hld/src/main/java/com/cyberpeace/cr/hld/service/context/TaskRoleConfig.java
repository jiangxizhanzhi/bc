package com.cyberpeace.cr.hld.service.context;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 角色配置
 */
@Data
public class TaskRoleConfig extends HldRoleConfig implements Serializable {

	private String key;

	private String type;

	private List<HldRoleConfig> children;

	public TaskRoleConfig(String name, List<HldRoleConfig> children) {
		this.setKey("");
		this.setType(name);
		this.setId(name);
		this.setName(name);
		this.setChildren(children);
		this.setAssetNodes(Lists.newArrayList());
		this.setVisibleNodes(Lists.newArrayList());
	}

}
