package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodePageReq extends PageReqBase implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("角色 红方/白方/蓝方")
	private String role;

	@ApiModelProperty("开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@ApiModelProperty("结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty("节点ID")
	private String nodeId;

}
