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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 获取得分响应
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class GetScoreRsp implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "学员id")
	private String userId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "学员姓名")
	private String name;

	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = "学员头像")
	private String userAvatar;

	/**
	 * 全部任务计数
	 */
	@ApiModelProperty(value = "任务数")
	private Integer allTaskCount;

	/**
	 * 完成任务计数
	 */
	@ApiModelProperty(value = "完成数")
	private Integer finishTaskCount;

	/**
	 * 成功任务计数
	 */
	private Integer successTaskCount;

	/**
	 * 失败任务计数
	 */
	private Integer failTaskCount;

	/**
	 * 主观评分
	 */
	@ApiModelProperty(value = "主观题得分")
	private Integer subjectiveScore;

	/**
	 * 客观评分
	 */
	@ApiModelProperty(value = "客观题得分")
	private Integer objectiveScore;

	/**
	 * 能力分数
	 */
	@ApiModelProperty(value = "能力得分")
	private Integer abilityScore;

	/**
	 * 能力分数列表
	 */
	private List<Integer> abilityScoreList;

	/**
	 * 综合评分
	 */
	@ApiModelProperty(value = "综合得分")
	private Integer synthesisScore;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "人员角色类型")
	private Integer type;

	/**
	 * 指数
	 */
	@ApiModelProperty(value = "排名")
	private Integer index;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 技能分数上下文列表
	 */
	@ApiModelProperty(value = "技能评分")
	private List<SkillScoreContext> skillScoreContextList;

	/**
	 * 用户任务列表
	 */
	@ApiModelProperty(value = "训练记录")
	private List<MissionSchemeObject<MissionContext>> userMissionList;

	/**
	 * 任务日志响应列表
	 */
	@ApiModelProperty(value = "动态事件")
	private List<TaskLogRsp> taskLogRspList;

	/**
	 * 全部训练得分
	 */
	@ApiModelProperty(value = "训练评分总分")
	private Integer allTrainScore;

	/**
	 * 全部得分
	 */
	@ApiModelProperty(value = "总分")
	private Integer allScore;

	/**
	 * 全部目得分
	 */
	@ApiModelProperty(value = "客观题总分")
	private Integer allObjectiveScore;

	/**
	 * 全部主观得分
	 */
	@ApiModelProperty(value = "主观题总分")
	private Integer allSubjectiveScore;

	/**
	 * 评分 备注
	 */
	@ApiModelProperty(value = "能力评分备注")
	private String scoreRemark;

	/**
	 * 技能分数上下文列表字符串
	 */
	private String skillScoreContextListString;

	/**
	 * 训练得分率
	 */
	private BigDecimal trainScoreRate;

	/**
	 * 能力分数率
	 */
	private Integer abilityScoreRate;

	/**
	 * 评分界面，每颗星代表多少分
	 */
	public static final Integer SCORE_PER_STAR = 4;

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
				.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum() * SCORE_PER_STAR;
		List<Integer> scoreList = JsonUtil.toJsonObjectArray(crScore.getSkillScore(), Integer.class);
		scoreList = CollectionUtils.isEmpty(scoreList) ? Arrays.asList(0, 0, 0, 0, 0) : scoreList;
		this.abilityScoreList = scoreList.stream().map(item -> item * SCORE_PER_STAR).collect(Collectors.toList());
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
