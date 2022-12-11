package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskEventReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("查询ID")
	private String queryId;

}
