package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

/**
 * 角色更新配置
 *
 * @author SN
 * @date 2023/05/12
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RoleUpdateConfig extends CrRoleNodesData implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String name;

	public CrRoleNodesData getNodes() {
		CrRoleNodesData nodesData = new CrRoleNodesData();
		nodesData.setAssetNodes(this.getAssetNodes());
		nodesData.setVisibleNodes(this.getVisibleNodes());
		return nodesData;
	}
}
