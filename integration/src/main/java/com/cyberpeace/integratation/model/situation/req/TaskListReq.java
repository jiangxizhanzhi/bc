package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskListReq implements Serializable {

	@ApiModelProperty("分页页码")
	private Integer page;

	@ApiModelProperty("每页条数")
	private Integer pageSize;

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("任务类型")
	private String taskType;

	@ApiModelProperty("任务模式")
	private String taskMode;

	@ApiModelProperty("任务状态")
	private String taskStatus;

	@ApiModelProperty("模糊查询")
	private String searchKey;

	public void setPerPage(Integer perPage) {
		this.pageSize = perPage;
	}

	public void setPattern(String pattern) {
		this.searchKey = pattern;
	}

}
