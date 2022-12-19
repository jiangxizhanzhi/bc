package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SchemeVisualizationBo implements Serializable {

	@ApiModelProperty(value = "可视化方案ID")
	private String schemeId;

	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;

	@ApiModelProperty(value = "可视化方案描述")
	private String schemeDesc;

	@ApiModelProperty(value = "分靶场ID")
	private String subRangeId;

	@ApiModelProperty(value = "分靶场名称")
	private String subRangeName;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "任务名称")
	private String taskName;

	@ApiModelProperty(value = "任务类型")
	private String taskType;

	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@ApiModelProperty(value = "科目名称")
	private String subjectName;

	@ApiModelProperty(value = "科目开始时间")
	private String startTime;

	@ApiModelProperty(value = "环境配置方案ID")
	private String envId;

	@ApiModelProperty(value = "GIS视角")
	private GisViewBo gisView;

}

@Data
class GisViewBo implements Serializable {

	@ApiModelProperty(value = "经度")
	private String longitude;

	@ApiModelProperty(value = "纬度")
	private String latitude;

	@ApiModelProperty(value = "高程")
	private String altitude;

	@ApiModelProperty(value = "倾斜值")
	private Float pitch;

	@ApiModelProperty(value = "偏移值")
	private Float yaw;

	@ApiModelProperty(value = "旋转值")
	private Float roll;
}
