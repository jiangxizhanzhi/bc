package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskSizeReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("查询条数")
	private Integer size;
}
