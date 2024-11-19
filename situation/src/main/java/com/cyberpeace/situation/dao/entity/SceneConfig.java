package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 场景配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("situation_scene_config")
public class SceneConfig extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	private String sceneId;

	/**
	 * 流量分析
	 *
	 * @see Boolean
	 */
	private Boolean flowAnalysis;

	/**
	 * 流量自动跑
	 *
	 * @see Boolean
	 */
	private Boolean flowAutoRun;

	public SceneConfig(String sceneId, Boolean flowAnalysis, Boolean flowAutoRun) {
		this.sceneId = sceneId;
		this.flowAnalysis = flowAnalysis;
		this.flowAutoRun = flowAutoRun;
	}

	public SceneConfig() {}
}
