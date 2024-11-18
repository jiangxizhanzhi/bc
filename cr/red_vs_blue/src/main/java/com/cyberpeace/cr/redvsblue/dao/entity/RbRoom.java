package com.cyberpeace.cr.redvsblue.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;

import lombok.Data;
import lombok.Getter;

/**
 * RB 会议室
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_rb_room")
public class RbRoom extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 模板id
	 *
	 * @see String
	 */
	private String templateId;

	/**
	 * 模板名称
	 *
	 * @see String
	 */
	private String templateName;

	/**
	 * 训练状态
	 *
	 * @see Integer
	 */
	private Integer trainingStatus;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 子类型
	 *
	 * @see Integer
	 */
	private Integer subType;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 预技能
	 *
	 * @see String
	 */
	private String preSkill;

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	private String trainSkill;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

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
	 * 启用录制
	 *
	 * @see Boolean
	 */
	private Boolean enableRecording;

	/**
	 * 支持 Partial Start
	 *
	 * @see Boolean
	 */
	private Boolean supportPartialStart;

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

	public RbRoom() {

	}

	public RbRoom(RbRoomContext input, RbTemplate template) {
		this.templateId = template.getResourceId();
		this.name = input.getName();
		this.description = input.getDescription();
		this.subType = input.getCategory();
		this.preSkill = JsonUtil.toJsonString(input.getPreSkill());
		this.trainSkill = JsonUtil.toJsonString(input.getTrainSkill());
		this.startTime = input.getStartTime();
		this.endTime = input.getEndTime();
		this.enableRecording = input.getEnableRecording();
		this.difficulty = input.getDifficult();
		this.createUserId = createUserId;
		this.localSceneId = localSceneId;
		this.logo = input.getLogo();
	}

	public boolean isUnopened() {
		return trainingStatus.equals(RbRoom.TrainingStatusCode.UNOPENED.getCode());
	}

	public boolean isPaused() {
		return trainingStatus.equals(TrainingStatusCode.PAUSE.getCode());
	}

	public boolean isOpening() {
		return trainingStatus.equals(RbRoom.TrainingStatusCode.RUNNING.getCode());
	}

	public boolean isEnded() {
		return trainingStatus.equals(RbRoom.TrainingStatusCode.ENDED.getCode());
	}

	public enum TrainingStatusCode {

		UNOPENED(4, "未开始"), RUNNING(2, "已开始"), ENDED(0, "已结束"), PAUSE(1, "已暂停"), RECOVERY(3, "恢复");

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
