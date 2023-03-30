package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationPublishInstanceRes extends SituationPublishInstance implements Serializable {

	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;

	@ApiModelProperty(value = "可视化方案描述")
	private String schemeDesc;

	@ApiModelProperty(value = "任务类型")
	private String taskType;

	@ApiModelProperty("原始模板ID")
	private String originTemplateId;

	@ApiModelProperty(value = "重演开始时间")
	private Date startTime;

	@ApiModelProperty(value = "重演结束时间")
	private Date endTime;

}
