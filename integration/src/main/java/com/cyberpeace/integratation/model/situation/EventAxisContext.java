package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EventAxisContext implements Serializable {

	@ApiModelProperty("事件ID")
	private String eventId;

	@ApiModelProperty("事件描述")
	private String eventDesc;

	@ApiModelProperty("事件时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date eventTime;

}
