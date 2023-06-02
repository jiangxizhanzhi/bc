package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
public class StudentScoreTrend {

	@ApiModelProperty("用户信息")
	private UserData userData;

	@ApiModelProperty("得分趋势列表")
	private List<ScoreTrend> scoreTrends;

	private Integer role;

	public StudentScoreTrend() {}

	public StudentScoreTrend(UserData user, List<CrTask> successList, Integer role,
			List<Date> timeList) {
		this.userData = user;
		this.scoreTrends = new ArrayList<>();
		this.role = role;
		Integer score = 0;
		successList.sort(Comparator.comparing(CrTask::getLastSubmitTime,Comparator.nullsLast(Date::compareTo)));
		int j = 0;
		for (int i = 1; i < timeList.size(); i++) {
			Date date = timeList.get(i);
			ScoreTrend scoreTrend = new ScoreTrend();
			for (; j < successList.size(); j++) {
				CrTask crTask = successList.get(j);
				if (date==null||crTask.getLastSubmitTime()==null||date.before(crTask.getLastSubmitTime())) break;
				score += crTask.getScore();
			}
			scoreTrend.setScore(score);
			scoreTrend.setScoreTime(date);
			scoreTrends.add(scoreTrend);
		}
	}
}
