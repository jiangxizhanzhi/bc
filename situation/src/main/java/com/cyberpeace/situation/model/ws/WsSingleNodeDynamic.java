package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.ElementInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsSingleNodeDynamic implements Serializable {

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("事件时间")
	private String time;

	@ApiModelProperty("事件名称")
	private String name;

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("事件类别")
	private String category;

	@ApiModelProperty("事件类型")
	private Integer type;

	@ApiModelProperty("节点id")
	private String elementId;

	@ApiModelProperty("节点队伍名称")
	private String groupName;

	@ApiModelProperty("节点信息（更新部分）")
	private ElementInfo elementInfo;

}
