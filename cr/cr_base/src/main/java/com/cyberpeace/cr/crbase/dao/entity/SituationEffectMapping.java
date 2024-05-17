package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("situation_effect_mapping")
public class SituationEffectMapping extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String rule;

	private String sourceEffect;

	private String targetEffect;

	private Boolean focus;

	private Integer color;

	private Integer lineType;

	private Integer lineWidth;
}
