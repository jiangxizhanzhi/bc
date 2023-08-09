package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_eval_template")
public class EvalTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String description;

	private String logo;

	private String topoId;

	private Integer categoryCode;

	private String stepConfig;

	private Boolean enableRecording;

	private String currentTaskId;

	private String createUserId;

	private String redVsBlueId;

	public void setLogoId(String logoId) {
		this.logo = logoId;
	}

}
