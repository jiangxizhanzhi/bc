package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardTemplateContext implements Serializable {

	@ApiModelProperty("模板ID")
	private String templateId;

	@ApiModelProperty("大屏ID")
	private String dashboardId;

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("可视化方案ID")
	private String schemeId;

	@ApiModelProperty("环境配置方案ID")
	private String envId;

	@ApiModelProperty("视角配置")
	private String viewAngle;

	public DashboardTemplateContext() {}

	public DashboardTemplateContext(SituationPublishInstance situationPublishInstance,
			DashboardAttribute dashboardAttribute) {
		this.dashboardId = situationPublishInstance.getResourceId();
		this.templateId = situationPublishInstance.getTemplateId();
		this.subRangeId = situationPublishInstance.getSubRangeId();
		this.taskId = situationPublishInstance.getTaskId();
		this.subjectId = situationPublishInstance.getSubjectId();
		this.schemeId = dashboardAttribute.getSchemeId();
		this.envId = dashboardAttribute.getEnvId();
		this.viewAngle = dashboardAttribute.getViewAngle();
	}

}
