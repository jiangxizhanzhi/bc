package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class NodeBo implements Serializable {

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id", required = true)
	private String nodeId;

	/**
	 * 节点操作系统类型
	 */
	@ApiModelProperty(value = "节点操作系统类型", required = true)
	private Integer type;

	/**
	 * 是否实体设备
	 */
	@ApiModelProperty(value = "是否实体设备", required = true)
	private Boolean isEntity;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value = "节点名称", required = true)
	private String nodeName;

	/**
	 * ip地址配置
	 */
	@ApiModelProperty(value = "IP配置")
	private List<IpBo> ipConfig;

	/**
	 * 用户模型列表
	 */
	@ApiModelProperty(value = "行为变量配置")
	private List<UserInstanceAddBo> userModels;
}
