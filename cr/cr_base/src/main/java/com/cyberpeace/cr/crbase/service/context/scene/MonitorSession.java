package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 监控中间模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MonitorSession implements Serializable {

	/**
	 * 帮助 URL
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "协助url")
	private String assistanceUrl;

	/**
	 * 监控 URL
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "监控url")
	private String monitorUrl;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "节点id")
	private String nodeId;

	/**
	 * CPU 百分比
	 *
	 * @see Double
	 */
	@ApiModelProperty("CPU使用率")
	private Double cpuPercent;

	/**
	 * 内存百分比
	 *
	 * @see Double
	 */
	@ApiModelProperty("内存使用率")
	private Double memoryPercent;

	/**
	 * 节点名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	/**
	 * 协议
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "会话协议")
	private String protocol;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景id")
	private String sceneId;

	/**
	 * 场景名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	/**
	 * 用户
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户id")
	private String user;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色类型")
	private Integer roleGroup;

	/**
	 * 化身
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "头像")
	private String avatar;

	/**
	 * ip地址
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "IP地址")
	private String ip;

}
