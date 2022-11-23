package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LcBehaviorRspBo implements Serializable {

	private String id;

	private String flowChartName;

	private String appName;

	private Integer deviceType;

	private Integer netModel;

	private Integer stepNum;

	private Integer consumeTime;

	private String creatorName;

	private String type;

	private String application;

	private Date createTime;
}
