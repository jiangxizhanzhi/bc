package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * 攻击动效
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Log4j2
@Data
public class AttackEffect implements Serializable {

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点id")
	private String elementId;

	/**
	 * 节点队伍名称
	 *
	 * @see String
	 */
	@ApiModelProperty("节点队伍名称")
	private String groupName;

	/**
	 * 区域id
	 *
	 * @see String
	 */
	@ApiModelProperty("区域id")
	private String areaId;

	/**
	 * 媒体资源类型 1-图片 2-视频
	 *
	 * @see Integer
	 */
	@ApiModelProperty("媒体资源类型 1-图片 2-视频")
	private Integer mediaType;

	/**
	 * 媒体资源路径
	 *
	 * @see String
	 */
	@ApiModelProperty("媒体资源路径")
	private String mediaUrl;

	/**
	 * 事件描述
	 *
	 * @see String
	 */
	@ApiModelProperty("事件描述")
	private String description;

	/**
	 * 线型
	 *
	 * @see LineStyle
	 */
	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	/**
	 * 展示区域 1-左上 2-左下 3-右上 4-右下
	 *
	 * @see Integer
	 */
	@ApiModelProperty("展示区域 1-左上 2-左下 3-右上 4-右下")
	private Integer direction;
}
