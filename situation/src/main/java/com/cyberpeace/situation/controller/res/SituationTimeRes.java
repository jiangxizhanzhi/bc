package com.cyberpeace.situation.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SituationTimeRes implements Serializable {

	@ApiModelProperty("天文时间")
	private String astronomicalTime;

	@ApiModelProperty("作战时间")
	private String combatTime;

	@ApiModelProperty("运行速率")
	private Integer rate;

	@ApiModelProperty("状态：（0：未开始、1：运行中、2：暂停中、3：结束）")
	private Integer status;
}
