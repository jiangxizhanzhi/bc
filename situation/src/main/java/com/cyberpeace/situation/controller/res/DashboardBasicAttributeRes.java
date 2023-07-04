package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.gis.MapSource;
import com.cyberpeace.situation.model.ViewAngle;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 大屏基本属性返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DashboardBasicAttributeRes implements Serializable {

	/**
	 * 大屏ID
	 */
	@ApiModelProperty("大屏ID")
	private String dashboardId;

	@ApiModelProperty("大屏模式 1-GIS/TOPO 2-单GIS 3-单TOPO")
	private Integer dashboardMode;

	@ApiModelProperty("大屏层级 1-综合态势 2-分靶场态势 3-任务态势")
	private Integer level;

	@ApiModelProperty("大屏标题")
	private String title;

	@ApiModelProperty("大屏副标题")
	private String subTitle;

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("任务类型")
	private String taskType;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("科目名称")
	private String subjectName;

	@ApiModelProperty("任务/科目开始时间")
	private Date startTime;

	@ApiModelProperty("可视化方案ID")
	private String schemeId;

	@ApiModelProperty("环境配置方案ID")
	private String envId;

	@ApiModelProperty("刷新时间（s）")
	private Integer refresh = 5;

	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

	@ApiModelProperty("地图数据源")
	private String mapSource = MapSource.GGIM.getName();

	@ApiModelProperty("特定事件列表")
	private List<String> eventList = Lists.newArrayList();

	public DashboardBasicAttributeRes(String dashboardId, Integer level, String title,
			String subTitle, String subRangeId, String taskId, Date startTime,
			ViewAngle viewAngle) {
		this.dashboardId = dashboardId;
		this.level = level;
		this.title = title;
		this.subTitle = subTitle;
		this.subRangeId = subRangeId;
		this.taskId = taskId;
		this.startTime = startTime;
		this.viewAngle = viewAngle;
		this.envId = "demo";
	}

	public DashboardBasicAttributeRes() {}

	public void setViewAngle(String viewAngle) {
		this.viewAngle = JsonUtil.toJsonObject(viewAngle, ViewAngle.class);
	}

	public void setMapSource(Integer mapSource) {
		this.mapSource = MapSource.getMapSourceByCode(mapSource).getName();
	}

	public void setMapSource(String mapSource) {
		try {
			Integer type = Integer.parseInt(mapSource);
			this.mapSource = MapSource.getMapSourceByCode(type).getName();
		} catch (NumberFormatException e) {
			this.mapSource = mapSource;
		}
	}
}
