package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("situation_dashboard_attribute")
public class DashboardAttribute implements Serializable {

	private String dashboardId;

	private Integer dashboardMode;

	private Integer level;

	private String title;

	private String subTitle;

	private String subRangeId;

	private String subRangeName;

	private String taskId;

	private String taskName;

	private String taskType;

	private String subjectId;

	private String subjectName;

	private Date startTime;

	private String topoId;

	private Integer refresh;

	private String longitude;

	private String latitude;

	private Integer altitude;

	private Integer angle;

}
