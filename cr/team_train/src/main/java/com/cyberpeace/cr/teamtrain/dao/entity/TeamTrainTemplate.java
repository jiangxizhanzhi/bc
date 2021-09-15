package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.suit.BaseEntity;

import lombok.Data;

@Data
@TableName("cr_team_train_template")
public class TeamTrainTemplate extends BaseEntity {

	/**
	 * 关联com.cyberpeace.cr.cebase.CrEvent的pk
	 * 原名 cr_event 原版用的是id 这里用resource_id
	 * */
	private String crEventId;

	/**
	 * 中台场景拓扑配置的id
	 * 原名 scene_config
	 * */
	private String sceneConfig;

	/**
	 * 时间线
	 * */
	private String timeLineConfig;
}
