package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.time.LocalTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为列表json字符串只存储行为的id和访问时间
 */
@Data
public class

BehaviorSimple implements Serializable {

	@ApiModelProperty(value = "编号")
	private String id;

	@ApiModelProperty(value = "访问时间")
	private LocalTime startTime;
}
