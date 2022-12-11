package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventReq extends NodeListApiReq implements Serializable {

	@ApiModelProperty("查询ID")
	private String queryId;

}
