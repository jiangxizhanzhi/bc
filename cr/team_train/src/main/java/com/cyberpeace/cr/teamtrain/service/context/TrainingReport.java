package com.cyberpeace.cr.teamtrain.service.context;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class TrainingReport {

	String teacherName = "";

	Date sceneStartTime;

	Integer trainingTime = 0;

	List<UserReport> users = Lists.newArrayList();
}
