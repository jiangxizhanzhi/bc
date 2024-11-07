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
 * @date 2024/11/07
 */
@Data
public class DashboardInfo implements Serializable {

	@ApiModelProperty("个人信息")
	private DashboardUser mine;

	@ApiModelProperty("好友信息")
	private List<DashboardFriend> friend;

	@ApiModelProperty("群组信息")
	private List<DashboardGroup> group;

	@ApiModelProperty("角色常量")
	private List<DashboardRole> roleConst;
}
