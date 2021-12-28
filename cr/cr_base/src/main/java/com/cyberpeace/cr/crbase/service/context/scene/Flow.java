package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单个流量
 */
@Data
public class Flow implements Serializable {

	@ApiModelProperty("节点id")
	private String nodeId;

	@ApiModelProperty("节点名称")
	private String nodeName;

	@ApiModelProperty("流量id")
	private String id;

	@ApiModelProperty("流量名称")
	private String name;

	@ApiModelProperty("流量描述")
	private String desc;

	@ApiModelProperty("交互类型 0:背景流量， 1：攻击流量，2：全流量")
	private Integer interactiveType;

	@ApiModelProperty("流量状态 -1：场景未开启 0：初始化，1：正在运行，2：正在停止， 3：已停止")
	private Integer status;

	@ApiModelProperty("状态描述")
	private String statusText;

	@ApiModelProperty("流量执行次数")
	private Integer count;

}
