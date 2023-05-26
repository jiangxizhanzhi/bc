package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class GetScoreRsp implements Serializable {

	@ApiModelProperty(value = "学员id")
	private String userId;

	@ApiModelProperty(value = "学员姓名")
	private String name;

	@ApiModelProperty(value = "学员头像")
	private String userAvatar;

	@ApiModelProperty(value = "任务数")
	private Integer allTaskCount;

	@ApiModelProperty(value = "完成数")
	private Integer finishTaskCount;

	@ApiModelProperty(value = "主观题得分")
	private Integer subjectiveScore;

	@ApiModelProperty(value = "客观题得分")
	private Integer objectiveScore;

	@ApiModelProperty(value = "能力得分")
	private Integer abilityScore;

	@ApiModelProperty(value = "综合得分")
	private Integer synthesisScore;

	public GetScoreRsp(UserData userData, List<CrTask> userTaskList,
			Map<String, Mission> missionMap, Map<String, CrScore> scoreMap) {
		this.userId = userData.getResourceId();
		this.name = userData.getRepName();
		this.userAvatar = userData.getLogo();
		this.allTaskCount = userTaskList.size();
		this.finishTaskCount = (int) userTaskList.stream().filter(CrTask::isFinished).count();
		this.subjectiveScore = (int) userTaskList.stream()
				.filter(a -> missionMap.containsKey(a.getMissionId())
						&& !missionMap.get(a.getMissionId()).isObjective())
				.mapToInt(CrTask::getScore).sum();
		this.objectiveScore = (int) userTaskList.stream()
				.filter(a -> missionMap.containsKey(a.getMissionId())
						&& missionMap.get(a.getMissionId()).isObjective())
				.mapToInt(CrTask::getScore).sum();
		this.abilityScore = scoreMap.containsKey(userId)
				? JsonUtil.toJsonObjectArray(scoreMap.get(userId).getSkillScore(), Integer.class)
						.stream().mapToInt(Integer::intValue).sum()
				: 0;
		this.abilityScore = scoreMap.containsKey(userId) ? scoreMap.get(userId).getFinalScore() : 0;

	}
}
