package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class SceneConfigRsp implements Serializable {

	/**
	 * 节点计数
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	/**
	 * 是否启动测试场景
	 */
	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;

	/**
	 * 资源占用数
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 总CPU
	 */
	@ApiModelProperty(value = "拓扑中总的cpu数量,单位个")
	private Integer totalCpu;

	/**
	 * 总磁盘
	 */
	@ApiModelProperty(value = "拓扑中总的磁盘大小,单位G")
	private Integer totalDisk;

	/**
	 * 总内存
	 */
	@ApiModelProperty(value = "拓扑中总的内存大小,单位M")
	private Integer totalRam;
}
