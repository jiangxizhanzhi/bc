package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.common.context.role.CrRoleSimple;
import com.cyberpeace.cr.crbase.service.context.task.TaskLogRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
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

	private Integer successTaskCount;

	private Integer failTaskCount;

	@ApiModelProperty(value = "主观题得分")
	private Integer subjectiveScore;

	@ApiModelProperty(value = "客观题得分")
	private Integer objectiveScore;

	@ApiModelProperty(value = "能力得分")
	private Integer abilityScore;

	private List<Integer> abilityScoreList;

	@ApiModelProperty(value = "综合得分")
	private Integer synthesisScore;

	@ApiModelProperty(value = "人员角色类型")
	private Integer type;

	@ApiModelProperty(value = "排名")
	private Integer index;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "技能评分")
	private List<SkillScoreContext> skillScoreContextList;

	@ApiModelProperty(value = "训练记录")
	private List<MissionSchemeObject<MissionContext>> userMissionList;

	@ApiModelProperty(value = "动态事件")
	private List<TaskLogRsp> taskLogRspList;

	@ApiModelProperty(value = "训练评分总分")
	private Integer allTrainScore;

	@ApiModelProperty(value = "总分")
	private Integer allScore;

	@ApiModelProperty(value = "客观题总分")
	private Integer allObjectiveScore;

	@ApiModelProperty(value = "主观题总分")
	private Integer allSubjectiveScore;

	@ApiModelProperty(value = "能力评分备注")
	private String scoreRemark;

	private String skillScoreContextListString;

	private BigDecimal trainScoreRate;

	private Integer abilityScoreRate;

	public GetScoreRsp() {}

	public GetScoreRsp(UserData userData, List<CrTask> userTaskList,
			Map<String, Mission> missionMap, Map<String, CrScore> scoreMap, CrRole crRole,
			UserSkillScoreContext userSkillScoreContext,
			List<MissionSchemeObject<MissionContext>> userMissionList,
			List<TaskLogRsp> taskLogRspList) {
		this.userId = userData.getResourceId();
		this.name = userData.getRepName();
		this.roleName = crRole.getName();
		this.userAvatar = userData.getLogo();
		this.allTaskCount = userTaskList.size();
		this.finishTaskCount = (int) userTaskList.stream().filter(CrTask::isFinished).count();
		this.successTaskCount = (int) userTaskList.stream().filter(CrTask::isSuccess).count();
		this.failTaskCount = finishTaskCount - successTaskCount;
		this.subjectiveScore = userTaskList.stream()
				.filter(a -> missionMap.containsKey(a.getMissionId())
						&& !missionMap.get(a.getMissionId()).isObjective() && a.getScore() != null)
				.mapToInt(CrTask::getScore).sum();
		this.objectiveScore = userTaskList.stream()
				.filter(a -> missionMap.containsKey(a.getMissionId())
						&& missionMap.get(a.getMissionId()).isObjective() && a.getScore() != null)
				.mapToInt(CrTask::getScore).sum();
		CrScore crScore = scoreMap.getOrDefault(userId, new CrScore());
		this.abilityScore = JsonUtil.toJsonObjectArray(crScore.getSkillScore(), Integer.class)
				.stream().filter(a -> a != null).mapToInt(Integer::intValue).sum();
		this.abilityScoreList = JsonUtil.toJsonObjectArray(crScore.getSkillScore(), Integer.class);
		if (CollectionUtils.isEmpty(this.abilityScoreList)) {
			this.abilityScoreList = Arrays.asList(0, 0, 0, 0, 0);
		}
		this.scoreRemark = crScore.getComment();
		this.synthesisScore = scoreMap.containsKey(userId) ? scoreMap.get(userId).getFinalScore()
				: 0;
		this.type = crRole.getRoleGroup();
		this.skillScoreContextList = userSkillScoreContext.getSkillScoreContextList();
		this.userMissionList = userMissionList;
		this.taskLogRspList = taskLogRspList;
		this.allTrainScore = 0;
		this.allObjectiveScore = 0;
		this.allSubjectiveScore = 0;

		for (String missionId : new CrRoleSimple(crRole).getMissionIds()) {
			Mission mission = missionMap.get(missionId);
			if (mission == null) {
				continue;
			}
			this.allTrainScore += mission.getScore();
			if (mission.isObjective()) {
				this.allObjectiveScore += mission.getScore();
			} else {
				this.allSubjectiveScore += mission.getScore();
			}
		}
		this.allScore = 100;
		this.trainScoreRate = crScore.getTrainingPercentage() != null
				? new BigDecimal(crScore.getTrainingPercentage()).setScale(0)
				: new BigDecimal(0);
		this.abilityScoreRate = crScore.getSkillPercentage() != null
				? Integer.parseInt(crScore.getSkillPercentage())
				: 0;
	}
}
