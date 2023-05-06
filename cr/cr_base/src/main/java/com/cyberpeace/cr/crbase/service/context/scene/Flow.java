package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流
 * 单个流量
 *
 * @author cyberpeace
 * @date 2023/05/06
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

	@ApiModelProperty("流量状态")
	private Integer status;

	@ApiModelProperty("状态描述")
	private String statusText;

	@ApiModelProperty("流量执行次数")
	private Integer count;

	@ApiModelProperty("流量分类")
	private Integer category;

	@ApiModelProperty("源Ip")
	private String sourceIp;

}
