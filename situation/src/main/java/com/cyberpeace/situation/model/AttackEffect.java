package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class AttackEffect implements Serializable {

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	private String elementId;

	/**
	 * 节点队伍名称
	 */
	@ApiModelProperty("节点队伍名称")
	private String groupName;

	/**
	 * 区域id
	 */
	@ApiModelProperty("区域id")
	private String areaId;

	/**
	 * 媒体资源类型 1-图片 2-视频
	 */
	@ApiModelProperty("媒体资源类型 1-图片 2-视频")
	private Integer mediaType;

	/**
	 * 媒体资源路径
	 */
	@ApiModelProperty("媒体资源路径")
	private String mediaUrl;

	/**
	 * 事件描述
	 */
	@ApiModelProperty("事件描述")
	private String description;

	/**
	 * 线型
	 */
	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	/**
	 * 展示区域 1-左上 2-左下 3-右上 4-右下
	 */
	@ApiModelProperty("展示区域 1-左上 2-左下 3-右上 4-右下")
	private Integer direction;
}
