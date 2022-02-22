package com.cyberpeace.cr.hld.service.context;

import lombok.Data;

import java.util.List;

@Data
public class HldTaskContext {

	private String taskName;

	private String attackTaskSenceDes;

	private HldUserContext controlUser;

	private HldUserContext assessUser;

	private List<HldRoleContext> redTrainUser;

	private List<HldRoleContext> blueTrainUser;

}
