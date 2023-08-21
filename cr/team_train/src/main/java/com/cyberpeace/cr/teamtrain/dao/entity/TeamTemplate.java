package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import lombok.Data;

/**
 * 
 * @TableName cr_team_template
 */
@TableName(value = "cr_team_template")
@Data
public class TeamTemplate extends BaseTable {

	/**
	 * 
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 
	 */
	private String businessTemplateId;

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
	private String roleConfig;

	/**
	 * 
	 */
	private Integer subType;

	/**
	 * 
	 */
	private String timeLineConfig;

	/**
	 *
	 */
	private Integer durationTime;

	/**
	 *
	 */
	private Integer peopleNumber;

	private Integer trainCategory;

	private Integer sceneType;

	public OperationModule getModule() {
		return getTrainCategory().equals(TeamRoom.TrainCategory.RED.getCode())
				? OperationModule.RED_TEAM
				: OperationModule.BLUE_TEAM;
	}

	public String getLogMsg() {
		return getTrainCategory().equals(TeamRoom.TrainCategory.RED.getCode())
				? LogContent.RED_TEAM_TRAIN_TEMPLATE.getMsg()
				: LogContent.BLUE_TEAM_TRAIN_TEMPLATE.getMsg();
	}

}