package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.dashboard.DashboardLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationPublishInstanceFilterReq extends PageReqBase implements Serializable {

	@ApiModelProperty(value = "大屏名称")
	private String name;

	@ApiModelProperty(value = "大屏类型 1-综合态势 2-任务态势")
	private Integer type;

	@ApiModelProperty(value = "大屏层级")
	private List<Integer> levels;

	@ApiModelProperty(value = "任务类型")
	private String category;

	@ApiModelProperty(value = "状态")
	private String status;

	@ApiModelProperty(name = "start_time", value = "过滤条件-开始时间")
	private Date startTime;

	@ApiModelProperty(name = "end_time", value = "过滤条件-结束时间")
	private Date endTime;

	@ApiModelProperty(value = "模式 1-实时 2-回放")
	private Integer replayMode;

	@ApiModelProperty(value = "是否重演大屏")
	private Boolean replay = false;

	public void setStart_time(String startTime) {
		this.startTime = TimeUtil.toDate1(startTime);
	}

	public void setEnd_time(String endTime) {
		this.endTime = TimeUtil.toDate1(endTime);
	}

	public void setType(Integer type) {
		this.type = type;
		this.levels = DashboardLevel.selectLevelByType(type);
	}
}
