package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.TimeUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationPublishInstanceFilterReq extends PageReqBase implements Serializable {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "任务类型")
	private String category;

	@ApiModelProperty(value = "状态")
	private String status;

	@ApiModelProperty(name = "start_time", value = "过滤条件-开始时间")
	private Date startTime;

	@ApiModelProperty(name = "end_time", value = "过滤条件-结束时间")
	private Date endTime;

	public void setStart_time(String startTime) {
		this.startTime = TimeUtil.toDate1(startTime);
	}

	public void setEnd_time(String endTime) {
		this.endTime = TimeUtil.toDate1(endTime);
	}
}
