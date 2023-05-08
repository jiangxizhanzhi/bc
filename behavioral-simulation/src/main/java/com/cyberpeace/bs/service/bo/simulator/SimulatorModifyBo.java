package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仪表修改请求体
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class SimulatorModifyBo implements Serializable {

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id", required = true)
	private String nodeId;

	/**
	 * 用户模型列表
	 * 包含行为变量配置
	 */
	@ApiModelProperty(value = "行为变量配置")
	private List<UserInstanceAddBo> userModels;
}
