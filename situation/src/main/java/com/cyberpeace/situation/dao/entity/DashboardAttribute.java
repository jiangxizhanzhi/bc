package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("situation_dashboard_attribute")
public class DashboardAttribute extends BaseTable implements Serializable {

	@TableId(value = "resource_id")
	private String resourceId;

	private Integer dashboardType;

	private Integer dashboardMode;

	private Integer level;

	private String title;

	private String subTitle;

	private String schemeId;

	private String schemeName;

	private String schemeDesc;

	private String envId;

	private String taskType;

	private String taskSubType = "0";

	private Date startTime;

	private Integer refresh;

	private String viewAngle;

}
