package com.cyberpeace.cr.singletrain.model.template;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cr_single_train_template")
public class Template {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 关联com.cyberpeace.cr.cebase.CrEvent的pk
	 * 原名 cr_event 原版用的是id 这里用resource_id
	 * */
	private String crEventId;

	/**
	 * 中台场景拓扑配置的id
	 * 原名 scene_config
	 * */
	private String sceneConfigId;

	/**
	 * 任务方案id
	 * 原名 missions_scheme
	 * */
	private String missionSchemeId;
}
