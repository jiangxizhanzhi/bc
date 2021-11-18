package com.cyberpeace.cr.teamtrain.controller;

import java.util.Date;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

@Data
public class MissionEvaluationRsp {

	/**
	 *submit_log_hash
	 */
	private String resourceId;

	private Date evaluateTime;

	private String roomId;

	private String submitUserName;

	private String submitUser;

	private String evaluatorName;

	private String evaluator;

	private String missionHash;

	private String submitLogHash;

	private Integer evaluateStatus;

	private Integer reason;

	private String detail;

	private Integer score;

	private Integer totalScore;

	private Mission missionData;

	private SubmitInfo submitInfo;

}
