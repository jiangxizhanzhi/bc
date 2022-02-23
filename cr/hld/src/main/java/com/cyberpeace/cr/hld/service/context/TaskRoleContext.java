package com.cyberpeace.cr.hld.service.context;

import java.util.List;

import lombok.Data;

@Data
public class TaskRoleContext {

	private String name;

	private String id;

	private List<TaskUserInfo> userList;

}
