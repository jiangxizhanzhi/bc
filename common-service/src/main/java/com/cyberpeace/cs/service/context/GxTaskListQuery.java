package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GxTaskListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "event_type", value = "事件类型 1-考试 2-课程")
	private Integer eventType;

	public void setEvent_type(Integer eventType) {
		this.eventType = eventType;
	}

}
