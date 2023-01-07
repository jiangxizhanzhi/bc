package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopoReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("环境配置方案ID")
	private String envId;

	@ApiModelProperty("子网ID")
	private String networkId;

	@ApiModelProperty("平台ID")
	private String platformId;

	@ApiModelProperty("层级")
	private Integer level;

	@ApiModelProperty("变更ID")
	private String updateId;

	public TopoReq(String subRangeId, String taskId, String subjectId, String envId) {
		this.setSubRangeId(subRangeId);
		this.setTaskId(taskId);
		this.setSubjectId(subjectId);
		this.envId = envId;
	}

	public TopoReq() {}

}
