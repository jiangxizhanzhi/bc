package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色更新配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/12
 * @see CrRoleNodesData
 * @see Serializable
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleUpdateConfig extends CrRoleNodesData implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
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
