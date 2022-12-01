package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("situation_scene_config")
//场景配置信息
public class SceneConfig extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	//资源编号
	private String resourceId;

	//场景编号
	private String sceneId;

	//获取流量攻击事件
	private Boolean flowAnalysis;

	//是否自动运行流量
	private Boolean flowAutoRun;

	//全参构造
	public SceneConfig(String sceneId, Boolean flowAnalysis, Boolean flowAutoRun) {
		this.sceneId = sceneId;
		this.flowAnalysis = flowAnalysis;
		this.flowAutoRun = flowAutoRun;
	}

	public SceneConfig() {}
}
