package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 监控中间模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MonitorSession implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/** 帮助 URL */
	@ApiModelProperty(value = "协助url")
	private String assistanceUrl;

	/** 监控 URL */
	@ApiModelProperty(value = "监控url")
	private String monitorUrl;

	/** 节点id */
	@ApiModelProperty(value = "节点id")
	private String nodeId;

	/** CPU 百分比 */
	@ApiModelProperty("CPU使用率")
	private Double cpuPercent;

	/** 内存百分比 */
	@ApiModelProperty("内存使用率")
	private Double memoryPercent;

	/** 节点名称 */
	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	/** 协议 */
	@ApiModelProperty(value = "会话协议")
	private String protocol;

	/** 场景id */
	@ApiModelProperty(value = "场景id")
	private String sceneId;

	/** 场景名称 */
	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	/** 用户 */
	@ApiModelProperty(value = "用户id")
	private String user;

	/** 用户名 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/** 岗位群 */
	@ApiModelProperty(value = "角色类型")
	private Integer roleGroup;

	/** 化身 */
	@ApiModelProperty(value = "头像")
	private String avatar;

	/** ip地址 */
	@ApiModelProperty(value = "IP地址")
	private String ip;

}
