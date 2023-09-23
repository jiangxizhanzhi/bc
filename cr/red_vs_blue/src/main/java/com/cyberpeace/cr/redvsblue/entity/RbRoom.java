package com.cyberpeace.cr.redvsblue.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_rb_room")
public class RbRoom extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String templateId;

	private String templateName;

	private Integer trainingStatus;

	private String name;

	private String createUserId;

	private Integer subType;

	private String description;

	private String preSkill;

	private String trainSkill;

	private Integer difficulty;

	private Date startTime;

	private Date endTime;

	private String localSceneId;

	private String copyedTopoId;

	private Boolean enableRecording;

	private Boolean supportPartialStart;

	private String logo;

	/**
	 * 复盘用的场景id，每次关闭场景时更新
	 */
	private String replaySceneId;

	/**
	 * 该训练过程的所有场景id
	 */
	private String sceneIdRecord;

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

	public List<String> getSceneIds() {
		if (this.sceneIdRecord == null) {
			return new ArrayList<>();
		}
		return JsonUtil.toJsonObjectArray(this.sceneIdRecord, String.class);
	}

	public void setSceneIds(List<String> sceneIds) {
		this.sceneIdRecord = JsonUtil.toJsonString(sceneIds);
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
