package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class SceneConfigRsp {

	/**
	 * 节点计数
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	/**
	 * 具有场景
	 */
	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;

	/**
	 * 资源占用数
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;
}
