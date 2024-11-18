package com.cyberpeace.cr.teamtrain.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;

import lombok.Data;
import lombok.Getter;

/**
 * 团队会议室
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_team_room")
@Data
public class TeamRoom extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 团队模板id
	 *
	 * @see String
	 */
	private String teamTemplateId;

	/**
	 * 团队模板名称
	 *
	 * @see String
	 */
	private String teamTemplateName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 子类型
	 *
	 * @see Integer
	 */
	private Integer subType;

	/**
	 * 预科技能
	 *
	 * @see String
	 */
	private String preSkills;

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	private String trainSkills;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 训练状态
	 *
	 * @see Integer
	 */
	private Integer trainingStatus;

	/**
	 * 时间线配置
	 *
	 * @see String
	 */
	private String timeLineConfig;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 修改用户id
	 *
	 * @see String
	 */
	private String modifyUserId;

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	private String localSceneId;

	/**
	 * 已复制拓扑id
	 *
	 * @see String
	 */
	private String copyedTopoId;

	/**
	 * 是否开启录屏
	 *
	 * @see Boolean
	 */
	private Boolean enableRecording;

	/**
	 * 图标
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 复盘用的场景id，每次关闭场景时更新
	 *
	 * @see String
	 */
	private String replaySceneId;

	public TeamRoom() {}

	public TeamRoom(TeamTrainRoomContext input, TeamTemplate template) {
		this.teamTemplateId = template.getResourceId();
		this.name = input.getName();
		this.description = input.getDescription();
		this.subType = input.getCategory();
		this.preSkills = JsonUtil.toJsonString(input.getPreSkill());
		this.trainSkills = JsonUtil.toJsonString(input.getTrainSkill());
		this.startTime = input.getStartTime();
		this.endTime = input.getEndTime();
		this.difficulty = input.getDifficult();
		this.timeLineConfig = template.getTimeLineConfig();
		this.enableRecording = input.getEnableRecording();
		this.createUserId = createUserId;
		this.modifyUserId = modifyUserId;
		this.localSceneId = localSceneId;
		this.logo = input.getLogo();
	}

	public boolean isUnopened() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.UNOPENED.getCode());
	}

	public boolean isOpening() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.OPENING.getCode());
	}

	public boolean isEnded() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.ENDED.getCode());
	}

	public enum TrainingStatusCode {

		UNOPENED(0, "未开始"), OPENING(1, "已开始"), ENDED(2, "已结束");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private TrainingStatusCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}