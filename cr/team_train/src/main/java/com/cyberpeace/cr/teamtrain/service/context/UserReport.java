package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import lombok.Data;

@Data
public class UserReport {

	private String userId;

	private String userName;

	private String roleName;

	private List<Integer> missionCompleted;

	private String comment;

	private Integer score;
}
