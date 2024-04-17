package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigRsp implements Serializable {

	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;

	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	@ApiModelProperty(value = "拓扑中总的cpu数量,单位个")
	private Integer totalCpu;

	@ApiModelProperty(value = "拓扑中总的磁盘大小,单位G")
	private Integer totalDisk;

	@ApiModelProperty(value = "拓扑中总的内存大小,单位M")
	private Integer totalRam;
}
