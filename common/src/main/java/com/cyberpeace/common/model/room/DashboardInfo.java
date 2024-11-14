package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data
public class DashboardInfo implements Serializable {

	/**
	 * 个人信息
	 */
	@ApiModelProperty("个人信息")
	private DashboardUser mine;

	/**
	 * 好友信息
	 */
	@ApiModelProperty("好友信息")
	private List<DashboardFriend> friend;

	/**
	 * 群组信息
	 */
	@ApiModelProperty("群组信息")
	private List<DashboardGroup> group;

	/**
	 * 角色常量
	 */
	@ApiModelProperty("角色常量")
	private List<DashboardRole> roleConst;
}
