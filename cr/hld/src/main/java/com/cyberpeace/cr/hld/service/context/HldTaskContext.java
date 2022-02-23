package com.cyberpeace.cr.hld.service.context;

import java.util.List;

import lombok.Data;

@Data
public class HldTaskContext {

	private String taskName;

	private String attackTaskSenceDes;

	private TaskUserInfo controlUser;

	private TaskUserInfo assessUser;

	private List<TaskRoleContext> redTrainUser;

	private List<TaskRoleContext> blueTrainUser;

}
