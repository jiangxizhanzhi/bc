package com.cyberpeace.situation.model.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsDefenseDynamic implements Serializable {

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("防御节点")
	private String defenseTargetId;

	@ApiModelProperty("防御类型")
	private Integer defenseType;

	@ApiModelProperty("是否聚焦")
	private Boolean lookAtTarget;

	@ApiModelProperty("持续时间（秒）")
	private Integer duration;

	@ApiModelProperty("是否成功")
	private Integer isSucceed;
}
