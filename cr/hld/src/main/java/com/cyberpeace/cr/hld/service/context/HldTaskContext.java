package com.cyberpeace.cr.hld.service.context;

import java.util.List;

import lombok.Data;

@Data
public class HldTaskContext {

	private String taskName;

	private String attackTaskSceneDes;

	private List<TaskUserInfo> controlUser;

	private List<TaskUserInfo> assessUser;

	private List<TaskRoleContext> redTrainUser;

	private List<TaskRoleContext> blueTrainUser;

}
