package com.cyberpeace.cr.teamtrain.service.context;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class TrainingReport {

	private String teacherName = "";

	private Date sceneStartTime;

	private Integer trainingTime = 0;

	private List<UserReport> users = Lists.newArrayList();
}
