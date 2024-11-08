package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单个流量
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class Flow implements Serializable {

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	private String nodeId;

	/**
	 * 节点名称
	 */
	@ApiModelProperty("节点名称")
	private String nodeName;

	/**
	 * id
	 */
	@ApiModelProperty("流量id")
	private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty("流量名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("流量描述")
	private String desc;

	/**
	 * 交互式类型
	 */
	@ApiModelProperty("交互类型 0:背景流量， 1：攻击流量，2：全流量")
	private Integer interactiveType;

	/**
	 * 状态
	 */
	@ApiModelProperty("流量状态")
	private Integer status;

	/**
	 * 状态发短信
	 */
	@ApiModelProperty("状态描述")
	private String statusText;

	/**
	 * 计数
	 */
	@ApiModelProperty("流量执行次数")
	private Integer count;

	/**
	 * 类别
	 */
	@ApiModelProperty("流量分类")
	private Integer category;

	/**
	 * 源ip地址
	 */
	@ApiModelProperty("源Ip")
	private String sourceIp;

}
