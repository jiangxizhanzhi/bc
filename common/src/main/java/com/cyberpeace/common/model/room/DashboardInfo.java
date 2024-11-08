package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

/**
 * 仪表板信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class DashboardInfo implements Serializable {

	/**
	 * 矿山
	 */
	@ApiModelProperty("个人信息")
	private DashboardUser mine;

	/**
	 * 朋友
	 */
	@ApiModelProperty("好友信息")
	private List<DashboardFriend> friend;

	/**
	 * 群
	 */
	@ApiModelProperty("群组信息")
	private List<DashboardGroup> group;

	/**
	 * 岗位常量
	 */
	@ApiModelProperty("角色常量")
	private List<DashboardRole> roleConst;
}
