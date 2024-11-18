package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单个流量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class Flow implements Serializable {

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点id")
	private String nodeId;

	/**
	 * 节点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("节点名称")
	private String nodeName;

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty("流量id")
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("流量名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("流量描述")
	private String desc;

	/**
	 * 交互式类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("交互类型 0:背景流量， 1：攻击流量，2：全流量")
	private Integer interactiveType;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("流量状态")
	private Integer status;

	/**
	 * 状态文本
	 *
	 * @see String
	 */
	@ApiModelProperty("状态描述")
	private String statusText;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("流量执行次数")
	private Integer count;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	@ApiModelProperty("流量分类")
	private Integer category;

	/**
	 * 源ip地址
	 *
	 * @see String
	 */
	@ApiModelProperty("源Ip")
	private String sourceIp;

}
