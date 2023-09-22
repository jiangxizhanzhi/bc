package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("situation_attack_message")
public class AttackMessage extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	//场景编号
	private String sceneId;

	//动效攻击类型
	private String type;

	//防御类型
	private String defenseType;

	//动效描述
	private String description;

	//源节点id
	private String sourceId;

	//目标节点id
	private String targetId;

	//间隔时间
	private Integer intervalTime;

	//持续时间
	private Integer durationTime;

	//是否聚焦
	private Boolean lookAtTarget;

	//线型
	private String lineType;

	//线的颜色
	private String lineColor;

	//线的宽度
	private Integer lineWidth;

	//关联步骤
	private Integer step;

	//多媒体数据
	private String mediaAnimation;

	//攻击或防御
	private String attackOrDefense;

	//防御节点
	private String defenseTargetId;

	//防御等级
	private Integer defenseLevel;

	//工具
	private String tool;

	//漏洞
	private String leak;

}
