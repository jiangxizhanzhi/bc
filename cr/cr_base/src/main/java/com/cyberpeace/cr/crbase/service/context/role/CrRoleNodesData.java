package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色拥有的节点
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CrRoleNodesData implements Serializable {

	/**
	 * 资产节点
	 */
	@ApiModelProperty(value = "资产节点")
	private List<CrRoleNode> assetNodes;

	/**
	 * 可见节点
	 */
	@ApiModelProperty(value = "战争迷雾")
	private List<CrRoleNode> visibleNodes;
}
