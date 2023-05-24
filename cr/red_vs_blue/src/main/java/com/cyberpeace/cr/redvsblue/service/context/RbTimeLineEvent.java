package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝时间线事件
 */
@Data
public class RbTimeLineEvent extends TimeLineEvent implements Serializable {

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

}
