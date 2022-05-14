package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import lombok.Data;

import java.util.List;

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

	public void setLogoId(String logoId) {
		this.logo = logoId;
	}

	public void setStepConfig(List<EvalTaskStepConfig> stepConfig){
		this.stepConfig = JsonUtil.arrayToString(stepConfig);
	}

	public void setStepConfig(String stepConfig){
		this.stepConfig = stepConfig;
	}

}
