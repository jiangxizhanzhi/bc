package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class SceneConfigRsp {

	/**
	 * 节点计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	/**
	 * 具有场景
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;

	/**
	 * 资源占用数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;
}
