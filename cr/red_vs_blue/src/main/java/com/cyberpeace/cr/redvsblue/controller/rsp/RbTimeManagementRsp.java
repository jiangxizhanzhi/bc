package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.Date;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class RbTimeManagementRsp extends BaseTable {

	private String resourceId;

	private String redVsBlue;

	private Date astronomicalTime;

	private Date combatTime;

	private Integer rate;

	private Integer status;

	private String createUser;

	private String modifyUser;

	private Date modifyTime;

	private Date updateTime;

	private Date currentCombatTime;

	private Date currentAstronomicalTime;

}
