package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.common.util.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("查询时间")
	private String queryTime;

	public void setCurrentTime(Date currentTime) {
		this.queryTime = TimeUtil.format1(currentTime);
	}

}
