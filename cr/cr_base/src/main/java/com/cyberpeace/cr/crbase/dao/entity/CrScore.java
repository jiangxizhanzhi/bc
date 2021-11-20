package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName(value = "cr_score")
public class CrScore extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private String userId;

	private Integer trainingScore;

	private Integer trainingPercentage;

	private Integer skillScore;

	private Integer skillPercentage;

	private String comment;

	private Integer finalScore;
}
