package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仪表新增请求体
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class SimulatorAddBo implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty(value = "场景id", required = true)
	private String sceneId;

	/**
	 * 场景名称
	 */
	@ApiModelProperty(value = "场景名称", required = true)
	private String sceneName;

	/**
	 * 节点列表
	 */
	@ApiModelProperty(value = "用户节点列表")
	private List<NodeBo> nodeList;
}
