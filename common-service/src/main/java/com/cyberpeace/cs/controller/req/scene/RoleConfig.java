package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleConfig implements Serializable {

	@ApiModelProperty(value = "角色名称")
	private String name;

	// /**
	// * 辅助前端切换不同角色任务编排的字段
	// */
	// @ApiModelProperty(value = "辅助前端切换不同角色任务编排的字段")
	// private String id;

	@ApiModelProperty(value = "资产节点")
	private List<RoleNode> assetNodes;

	@ApiModelProperty(value = "战争迷雾")
	private List<RoleNode> visibleNodes;

	public RoleNodeConfig getNodes() {
		RoleNodeConfig config = new RoleNodeConfig();
		config.setAssetNodes(this.assetNodes);
		config.setVisibleNodes(this.visibleNodes);
		return config;
	}
}

@Data
class RoleNode implements Serializable {

	@ApiModelProperty(value = "节点id")
	private String id;

	@ApiModelProperty(value = "节点类型")
	private Integer category;
}

@Data
class RoleNodeConfig implements Serializable {

	private List<RoleNode> assetNodes;

	private List<RoleNode> visibleNodes;
}
