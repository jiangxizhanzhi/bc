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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DashboardInfo implements Serializable {

	/**
	 * 矿山
	 *
	 * @see DashboardUser
	 */
	@ApiModelProperty("个人信息")
	private DashboardUser mine;

	/**
	 * 朋友
	 *
	 * @see List<DashboardFriend>
	 */
	@ApiModelProperty("好友信息")
	private List<DashboardFriend> friend;

	/**
	 * 组
	 *
	 * @see List<DashboardGroup>
	 */
	@ApiModelProperty("群组信息")
	private List<DashboardGroup> group;

	/**
	 * 岗位常量
	 *
	 * @see List<DashboardRole>
	 */
	@ApiModelProperty("角色常量")
	private List<DashboardRole> roleConst;
}
