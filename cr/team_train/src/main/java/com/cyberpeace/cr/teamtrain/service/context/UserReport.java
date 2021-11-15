package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import lombok.Data;

@Data
public class UserReport {

	String userId;

	String userName;

	String roleName;

	List<Integer> missionCompleted;

	String comment;

	Integer score;
}
