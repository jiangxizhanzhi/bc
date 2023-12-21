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

	private String sceneId;

	private String type;

	private String description;

	private String sourceId;

	private String targetId;

	private Integer intervalTime;

	private Integer durationTime;

	private Boolean lookAtTarget;

	private String lineType;

	private String lineColor;

	private String lineProcessColor;

	private Integer lineWidth;

	private String mediaAnimation;

}
