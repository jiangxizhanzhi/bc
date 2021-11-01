package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.model.room.TeamTrainRoomReq;

import lombok.Data;

/**
 * 
 * @TableName cr_team_room
 */
@TableName(value = "cr_team_room")
@Data
public class TeamRoom implements Serializable {

	/**
	 * 
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 
	 */
	private String teamTemplateId;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Integer subType;

	/**
	 * 
	 */
	private String preSkills;

	/**
	 * 
	 */
	private String trainSkills;

	/**
	 * 
	 */
	private Date startTime;

	/**
	 * 
	 */
	private Date endTime;

	/**
	 * 
	 */
	private Integer difficulty;

	/**
	 * 
	 */
	private String timeLineConfig;

	/**
	 * 
	 */
	private String createUserId;

	/**
	 * 
	 */
	private String modifyUserId;

	/**
	 * 
	 */
	private String localSceneId;

	/**
	 *
	 */
	private String copyedTopoId;

	public TeamRoom() {}

	public TeamRoom(TeamTrainRoomReq input, TeamTemplate template,
			BusinessTemplate businessTemplate) {
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
		// this.createUserId = createUserId;
		// this.modifyUserId = modifyUserId;
		// this.localSceneId = localSceneId;
		this.copyedTopoId = businessTemplate.getTopoId();
	}
}