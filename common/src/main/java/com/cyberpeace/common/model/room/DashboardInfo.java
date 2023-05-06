package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仪表盘上信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class DashboardInfo implements Serializable {

	@ApiModelProperty("个人信息")
	private DashboardUser mine;

	@ApiModelProperty("好友信息")
	private List<DashboardFriend> friend;

	@ApiModelProperty("群组信息")
	private List<DashboardGroup> group;
}
