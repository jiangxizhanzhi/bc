package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 学员得分趋势
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class StudentScoreTrend {

	/**
	 * 用户数据
	 */
	@ApiModelProperty("用户信息")
	private UserData userData;

	/**
	 * 分数趋势
	 */
	@ApiModelProperty("得分趋势列表")
	private List<ScoreTrend> scoreTrends;

	/**
	 * 岗位
	 */
	private Integer role;
	/**
	 * 分数总和
	 */
	@ApiModelProperty("最终得分用于排序")
	private Integer scoreSum;


	public StudentScoreTrend() {}

	public StudentScoreTrend(UserData user, List<CrTask> successList, Integer role,
			List<Date> timeList,List<String> timeStringList) {
		this.userData = user;
		this.scoreTrends = new ArrayList<>();
		this.role = role;
		Integer score = 0;
		successList.sort(Comparator.comparing(CrTask::getLastSubmitTime,Comparator.nullsLast(Date::compareTo)));
		int j = 0;
		for (int i = 0; i < timeList.size(); i++) {
			Date date = timeList.get(i);
			ScoreTrend scoreTrend = new ScoreTrend();
			for (; j < successList.size(); j++) {
				CrTask crTask = successList.get(j);
				if (date==null||crTask.getLastSubmitTime()==null||date.before(crTask.getLastSubmitTime())) break;
				score += crTask.getScore();
			}
			scoreTrend.setScore(score);
			scoreTrend.setScoreTime(timeStringList.get(i));
			scoreTrends.add(scoreTrend);
		}
		scoreSum = score;
	}
}
