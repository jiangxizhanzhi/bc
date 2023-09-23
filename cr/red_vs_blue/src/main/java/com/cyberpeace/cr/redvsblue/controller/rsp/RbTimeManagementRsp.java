package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.Date;

import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTimeManagementRsp extends BaseTable {

	@ApiModelProperty(value = "时间管理id")
	private String resourceId;

	@ApiModelProperty(value = "房间id")
	private String redVsBlue;

	@ApiModelProperty(value = "天文时间")
	private Date astronomicalTime;

	@ApiModelProperty(value = "作战时间")
	private Date combatTime;

	@ApiModelProperty(value = "速率")
	private Integer rate;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "创建者id")
	private String createUser;

	@ApiModelProperty(value = "修改者id")
	private String modifyUser;

	@ApiModelProperty(value = "修改时间")
	private Date modifyTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "当前作战时间")
	private Date currentCombatTime;

	@ApiModelProperty(value = "当前天文时间")
	private Date currentAstronomicalTime;
}
