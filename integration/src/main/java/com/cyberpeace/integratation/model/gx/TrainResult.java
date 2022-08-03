package com.cyberpeace.integratation.model.gx;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;

import lombok.Data;

@Data
public class TrainResult implements Serializable {

	private String duration;

	private String trainTime;

	private String registerTime;

	private String scoreType;

	private List<TrainScore> scores;

	private String softId;

	private String taskId;

	public TrainResult() {}

	public TrainResult(Double duration, String taskId, List<TrainScore> scores, Double trainTime) {
		this.duration = duration.toString();
		this.trainTime = String.format("%.1f", trainTime);
		this.registerTime = TimeUtil.format9(new Date());
		this.scoreType = "3";
		this.scores = scores;
		this.softId = SystemPropUtil.getProp("SOFT_ID");
		this.taskId = taskId;
	}

}
