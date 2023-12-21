package com.cyberpeace.situation.model.ws;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.AttackEffect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsAttackEffectDynamic implements Serializable {

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("事件时间")
	private Long time;

	@ApiModelProperty("事件名称")
	private String name;

	@ApiModelProperty("模板id")
	private String id;

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("事件类别")
	private String category;

	@ApiModelProperty("事件类型")
	private Integer type;

	@ApiModelProperty("攻击效果列表")
	private List<AttackEffect> effectList;

}
