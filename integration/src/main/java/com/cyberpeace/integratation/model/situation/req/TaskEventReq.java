package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskEventReq implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("查询ID")
	private String queryId;

}
