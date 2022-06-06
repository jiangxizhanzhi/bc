package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class AttackEffect implements Serializable {

	@ApiModelProperty("节点id")
	private String elementId;

	@ApiModelProperty("节点队伍名称")
	private String groupName;

	@ApiModelProperty("区域id")
	private String areaId;

	@ApiModelProperty("媒体资源类型 1-图片 2-视频")
	private Integer mediaType;

	@ApiModelProperty("媒体资源路径")
	private String mediaUrl;

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("线型")
	private LineStyle lineStyle;
}
