package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EvalActionProsecuteRsp {

	@ApiModelProperty(value = "记录id")
	private String resourceId;

	@ApiModelProperty(value = "用户-姓名")
	@NotNull
	private String name;

	@ApiModelProperty(value = "节点/任务名称")
	@NotNull
	private String actionName;

	@ApiModelProperty(value = "操作类型 1-12 分别对应 1-开始、2-中止、3-暂停、4-继续、6-完成、8-新增")
	private String operateType;

	@ApiModelProperty(value = "操作时间")
	@NotNull
	private Date createTime;

	@ApiModelProperty(value = "操作内容")
	private String content;

}
