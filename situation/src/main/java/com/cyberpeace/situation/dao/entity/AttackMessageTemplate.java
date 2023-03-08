package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_attack_message_template")
public class AttackMessageTemplate extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	//动效攻击类型
	private String type;

	//防御类型
	private String defenseType;

	//动效描述
	private String description;

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

//	//关联实景步骤
//	private Integer step;

}
