package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;
import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class RbRoomRoleRes {

	private String resourceId;

	private String name;

	private Integer roleGroup;

	private List<String> users;

	private List<UserData> usersData;

	private List<RbRoomRoleRes> children = Lists.newArrayList();

}
