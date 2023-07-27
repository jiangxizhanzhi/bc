package com.cyberpeace.situation.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowLogReq implements Serializable {

	@ApiModelProperty(value = "场景id", required = true)
	@NotEmpty
	private String sceneId;

	@ApiModelProperty("起始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("每页数据量")
	private Integer perPage = 10;

	public FlowLogReq(String sceneId) {
		this.sceneId = sceneId;
	}

	public FlowLogReq() {}
}
