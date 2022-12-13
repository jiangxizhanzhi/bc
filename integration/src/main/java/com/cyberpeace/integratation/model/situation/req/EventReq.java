package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("查询时间")
	private Date queryTime;

}
