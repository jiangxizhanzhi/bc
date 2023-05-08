package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * cr角色节点数据
 * 角色拥有的节点
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CrRoleNodesData implements Serializable {

	@ApiModelProperty(value = "资产节点")
	private List<CrRoleNode> assetNodes;

	@ApiModelProperty(value = "战争迷雾")
	private List<CrRoleNode> visibleNodes;
}
