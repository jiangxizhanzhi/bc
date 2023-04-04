package com.cyberpeace.cr.crbase.service.context.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainingAssetAccount implements Serializable {

	@ApiModelProperty("操作系统数量")
	private Integer operatingSystemCount;

	@ApiModelProperty("业务节点数量")
	private Integer businessNodesCount;

	@ApiModelProperty("网络节点数量")
	private Integer networkNodeCount;

	@ApiModelProperty("安全节点数量")
	private Integer securityNodeCount;

}
