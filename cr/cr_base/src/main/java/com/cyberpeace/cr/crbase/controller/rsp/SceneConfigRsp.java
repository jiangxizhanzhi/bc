package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigRsp {

	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;

	@ApiModelProperty(value = "cpu核心数量")
	private Integer cpu;

	@ApiModelProperty(value = "总磁盘大小")
	private Integer disk;

	@ApiModelProperty(value = "总内存")
	private Integer ram;
}
