package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 监控中间模型
 */
@Data
public class MonitorSession implements Serializable {

	@ApiModelProperty(value = "协助url")
	private String assistanceUrl;

	@ApiModelProperty(value = "监控url")
	private String monitorUrl;

	@ApiModelProperty(value = "节点id")
	private String nodeId;

	@ApiModelProperty("CPU使用率")
	private Double cpuPercent;

	@ApiModelProperty("内存使用率")
	private Double memoryPercent;

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	@ApiModelProperty(value = "会话协议")
	private String protocol;

	@ApiModelProperty(value = "场景id")
	private String sceneId;

	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	@ApiModelProperty(value = "用户id")
	private String user;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "IP地址")
	private String ip;

}
