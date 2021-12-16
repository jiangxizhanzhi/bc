package com.cyberpeace.cr.redvsblue.service.context.room;

import java.util.List;

import lombok.Data;

@Data
public class DashboardInfo {

	private DashboardUser mine;

	private DashboardFriend friend;

	private List<DashboardGroup> group;
}
